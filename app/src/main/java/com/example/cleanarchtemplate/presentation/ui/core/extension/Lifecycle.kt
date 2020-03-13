package com.example.cleanarchtemplate.presentation.ui.core.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.cleanarchtemplate.domain.core.Failure
import com.example.cleanarchtemplate.presentation.viewmodel.core.HandleOnce

/**
 * Для содержания extension функций, связанных с жизненным циклом
 * @see <a href="https://kotlinlang.ru/docs/reference/extensions.html">Функции расширения Kotlin</a>
 */

/**
 * Установка слушателя для данных(LiveData) успешного выполнения задач.
 * Присутствует параметризированные типы:
 * * T - наследующийся от Any
 * * L - наследующийся от LiveData с типом T.
 *
 * @param L - данные переданные в параметре
 * @param body - функция принимающая параметр T
 *
 * @author EvgenySamarin [github](https://github.com/EvgenySamarin)
 * @since 20190913 v2
 */
fun <T : Any, L : LiveData<T>> LifecycleOwner.onSuccess(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

/**
 * Установка слушателя для данных(LiveData) неудачного выполнения задач(ошибки).
 * Присутствует параметризированный тип
 * * L - наследующийся от LiveData с типом HandleOnce<Failure>.
 *
 * Выполняется null-безопасный вызов let(…), предотвращающий повторную обработку одних и тех же
 * данных(отрисовку ошибки).
 * @param body - функция, (Принимает Failure, ничего не возвращает)
 * @param L - данные переданные в параметре
 *
 * @author EvgenySamarin [github](https://github.com/EvgenySamarin)
 * @since 20190913 v1
 */
fun <L : LiveData<HandleOnce<Failure>>> LifecycleOwner.onFailure(
    liveData: L, body: (Failure?) -> Unit
) = liveData.observe(this,
    Observer {
        it.getContentIfNotHandled()?.let(body)
    })