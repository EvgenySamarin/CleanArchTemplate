package com.example.cleanarchtemplate.domain.core

/**
 * Base Class for handling errors/failures/exceptions.
 * Класс нужен для передачи маркера об ошибке с дальнейшей ее обработкой.
 *
 * Пример:
 *
 * API запросы подразумевают риск получения ошибок: сервер не доступен, нет подключения к сети.
 * При их получении мы искусственно создаем объекты ошибок, которые можно удобно обработать
 * (Выводить разные тосты в зависимости от типа объекта).
 *
 * @author EvgenySamarin
 * @since 20190905 v2
 */
sealed class Failure {
    object ServerError : Failure()
    object IncorrectLoginInfoError : Failure()
    object UnknownLoginError : Failure()
    object NoContentError : Failure()
    object NetworkConnectionError : Failure()
    object NoActiveSession : Failure()
    object JsonParsingError : Failure()
    object TooManyRequestError : Failure()
    object NotAllowedError : Failure()
    object FilePickError : Failure()
}