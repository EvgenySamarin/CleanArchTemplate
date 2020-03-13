package com.example.cleanarchtemplate.presentation.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cleanarchtemplate.R
import com.example.cleanarchtemplate.presentation.ui.home.HomeActivity
import kotlinx.serialization.json.Json.Default.context

/**
 * Responsible for show app foreground icon while base classes loading.
 * Can contains base logic of onboarding or sessions logic.
 *
 * @author EvgenySamarin
 * @since 20200312 v1
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationContext.startActivity<HomeActivity>(newTask = true)
    }
}

/**
 * вспомогательная extension функция, которая запускает activity. Формирует intent
 *
 * @param newTask флаг очистки стека по умолчанию стэк включен
 * @param args аргументы, которые будет переданы в параметре 'args'
 * @param T передаётся название класса activity
 */
private inline fun <reified T> Context.startActivity(
    newTask: Boolean = false, args: Bundle? = null
) {
    this.startActivity(Intent(this, T::class.java).apply {
        if (newTask) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        putExtra("args", args)
    })
}