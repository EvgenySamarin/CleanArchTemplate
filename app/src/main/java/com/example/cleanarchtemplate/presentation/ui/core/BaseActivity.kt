package com.example.cleanarchtemplate.presentation.ui.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.cleanarchtemplate.BuildConfig
import com.example.cleanarchtemplate.R
import com.example.cleanarchtemplate.domain.core.Failure
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Базовый компонент activity в нём хранится основной layout а также базовый вложенный фрагмент,
 * который в процессе работы программы может быть изменён на конкретную реализацию. При создании
 * активности мы кладём наш фрагмент в в базовый контейнер
 *
 * @author EvgenySamarin
 * @since 20190918 v2
 */
abstract class BaseActivity : AppCompatActivity() {
    abstract var navGraphId: Int

    //consumer of the activity results like media files and other content
    var activityResultConsumer: BaseFragment? = null

    open val contentId = R.layout.activity_base
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var permissionManager: PermissionManager

    lateinit var appBarConfiguration: AppBarConfiguration
    private var toolbar: Toolbar? = null
    private var progressBar: ProgressBar? = null
    private var rootLayout: View? = null
    private var appBarLayout: AppBarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(contentId)

        toolbar = window.decorView.findViewById(R.id.toolbar)
        progressBar = window.decorView.findViewById(R.id.progress_bar)
        rootLayout = window.decorView.findViewById(R.id.root_layout)
        appBarLayout = window.decorView.findViewById(R.id.include)
        setSupportActionBar(toolbar) //подключаем кастомный тулбар

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment? ?: return

        val navController = host.navController
        navController.graph = navController.navInflater.inflate(navGraphId)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        if (toolbar != null) setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResultConsumer?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)

    fun showProgress() = progressStatus(View.VISIBLE)
    fun hideProgress() = progressStatus(View.GONE)

    /** установить соответствующий статус для view */
    fun progressStatus(viewStatus: Int) {
        progressBar?.visibility = viewStatus
    }

    /**
     * Функция обработки различных ошибок. Используется для обработки ошибок с liveData. В основном
     * при работе с корутинами. Отображает сообщенгие в зависимости от типа ошибки
     */
    fun handleFailure(failure: Failure?) {
        hideProgress()

        when (failure) {
            is Failure.ServerError -> showMessage(getString(R.string.ERROR_BASE_COMMON))
            is Failure.NetworkConnectionError -> showMessage(getString(R.string.ERROR_BASE_NO_CONNECTION))
            is Failure.NoActiveSession -> showMessage(getString(R.string.ERROR_BASE_LOCAL_CACHE_CONNECT))
            is Failure.NoContentError -> showMessage(getString(R.string.ERROR_BASE_NO_CONTENT_ERROR))
            is Failure.JsonParsingError -> showMessage(getString(R.string.ERROR_BASE_JSON_PARSING))
            is Failure.FilePickError -> showMessage(getString(R.string.ERROR_BASE_FILE_PICK))
        }
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * Отобразить сообщение для пользователя
     * @param message сообщение
     */
    fun showMessage(message: String) {
        rootLayout?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    /**
     * Show message with action
     * @param message displayed message
     * @param actionTitle title of action button
     * @param duration duration in mills
     * @param action action which can be invoked
     *
     * @author EvgenySamarin
     * @since 20191231 v1
     */
    fun showMessage(
        message: String,
        actionTitle: String,
        duration: Int = Snackbar.LENGTH_LONG,
        action: (View) -> Unit
    ) {
        rootLayout?.let {
            Snackbar.make(it, message, duration).setAction(actionTitle, action).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.requestObject?.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }


    /** Поучить ViewModel типа T из фабрики моделей и выполнить для неё функцию переданную в параметре*/
    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProvider(this, viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }

    /** @since 20200131 v1: get view model of T type which will work in the "activity" scope */
    inline fun <reified T : ViewModel> getViewModel(): T =
        ViewModelProvider(this).get(T::class.java)

    companion object {
        const val BASE_UPLOAD_URL = BuildConfig.SERVER_UPLOAD
    }
}

/**
 * Extension функция, которая передает функцию body в тело активити BaseActivity, позволяя
 * вызывать ее методы.
 * @param block функция BaseActivity.()
 */
inline fun Activity?.base(block: BaseActivity.() -> Unit) {
    (this as? BaseActivity)?.let(block)
}
