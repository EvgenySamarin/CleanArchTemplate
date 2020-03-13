package com.example.cleanarchtemplate.presentation.viewmodel.core

/**
 * Класс-обертка. Гарантирует возвращение результата только единожды. Предотвращает повторное
 * получение данных.
 *
 * Слушатели LiveData срабатывают при смене конфигурации. В таком случае все данные передаются в
 * UI повторно. Для предотвращения повторного отображения тоста об одной и той же ошибке при
 * повороте устройства мы и используем этот класс.
 *
 * @param content данные над которыми будет осуществлятся работа
 *
 * @author EvgenySamarin
 * @since 20190905 v1
 */
open class HandleOnce<out T>(private val content: T) {

    private var hasBeenHandled = false //были ли переданы данные

    /** @return Return content and prevents its use again. */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
    }
}