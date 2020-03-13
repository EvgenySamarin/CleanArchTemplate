package com.example.cleanarchtemplate.presentation.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchtemplate.BuildConfig
import com.example.cleanarchtemplate.domain.core.Failure
import com.example.cleanarchtemplate.domain.core.entity.ServerResponseEntity
import javax.inject.Inject

/**
 * Абстрактный базовый класс для фрагментов, описывает общую логику для всех фрагментов
 *
 * @author EvgenySamarin [github](https://github.com/EvgenySamarin)
 * @since 20190913 v5
 */
abstract class BaseFragment : Fragment() {
    /** link to layout resource which would be inflated after init of class */
    abstract val layoutId: Int
    private val showToolbar = true


    //Инициализируется при помощи Dagger
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, container, false)

    override fun onResume() {
        super.onResume()

        base {
            if (showToolbar) supportActionBar?.show() else supportActionBar?.hide()
        }
    }

    /** @since 20200306 v1: show progress */
    fun showProgress() = base { progressStatus(View.VISIBLE) }

    /** @since 20200306 v1: hide progress */
    fun hideProgress() = base { progressStatus(View.GONE) }

    /**
     * Функция обработки ошибок, перехваченных из viewModel.
     * Вызывает функцию у родительской activity
     */
    open fun handleFailure(failure: Failure?) = base { handleFailure(failure) }

    /** @since 20200307 v1: handle common server response */
    open fun handleCommonServerResponse(serverResponseEntity: ServerResponseEntity?) {
        serverResponseEntity?.message?.let { base { showMessage(it) } }
    }

    /**
     * Функция, которая создает ViewModel и применяет к ней функцию высшего порядка,
     * переданную в параметрах(vm.body()).
     * Присутствует параметризированный вещественный(refied) тип T, который наследуется от ViewModel.
     *
     * @see <a href="http://qaru.site/questions/632242/how-does-the-reified-keyword-in-kotlin-work">
     *     Пример использования refield</a>
     *
     * @param body функция которая должна быть реализована в экземпляре T: ViewModel. Эта функция
     * будет выполнена после получения instance T
     * @return T (параметр) в нашем случае ViewModel
     */
    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProvider(this, viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }

    /** @since 20200131 v1: get view model of T type which will work in the "activity" scope */
    inline fun <reified T : ViewModel> getViewModel(): T =
        ViewModelProvider(this).get(T::class.java)

    /**
     * Функция вызывает функцию из базового класса BaseActivity на уровне текущей реализации activity
     */
    inline fun base(block: BaseActivity.() -> Unit) {
        activity.base(block)
    }

    /**
     * Позволяет выводить список сгруппированный по определённой строке.
     * Сверяет каждый элемент списка со строкой, полученной из элемента указанным образом, и
     * возращает коллекцию, сгруппированную по этой строке.
     *
     * @param list список, который требуется сгруппировать
     * @param getGroupBy функция получения объекта по которому будет происходить группировка из
     * элемента списка
     *
     * @author EvgenySamarin [github](https://github.com/EvgenySamarin)
     * @since 20191017 v2
     * @see <a href="https://krtkush.com/2016/07/08/android-recyclerview-grouping-data.html">
     *     Android RecyclerView - Grouping Data</a>
     */
    fun <ItemEntity> groupByAnyIntoHashMap(
        list: List<ItemEntity>, getGroupBy: (listItem: ItemEntity) -> Any
    ): HashMap<Any, ArrayList<ItemEntity>> {
        val groupedHashMap = HashMap<Any, ArrayList<ItemEntity>>()
        list.forEach {
            val groupBy = getGroupBy(it)
            if (groupedHashMap.containsKey(groupBy)) groupedHashMap[groupBy]?.add(it)
            else groupedHashMap[groupBy] = ArrayList<ItemEntity>().apply { add(it) }
        }
        return groupedHashMap
    }

    companion object {
        const val BASE_UPLOAD_URL = BuildConfig.SERVER_UPLOAD
    }
}