package com.example.cleanarchtemplate.data.remote.core

import com.example.cleanarchtemplate.domain.core.Either
import com.example.cleanarchtemplate.domain.core.Failure
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Выполняет сетевые запросы. А также производит базовую их обработку. В том числе обработку
 * поступивших ошибок. Класс предоставляется во все части программы с помощью Dagger RemoteModule
 *
 * @author EvgenySamarin
 * @since 20190905 v1
 */
@Singleton
class Request @Inject constructor(){
    /** вспомогательная функция для проверки сети и вызова fun execute */
    fun <T, R> make(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        return execute(call, transform)
    }

    /**
     * Выполняет сетевой запрос с помощью переданного в параметрах call (call.execute()).
     * В блоке catch формирует маркеры ошибок для дальнейшей обработки
     *
     * Функция высшего порядка для трансформации transform(принимает T, возвращает R).
     * Возвращает Either<Failure, R>.
     */
    private fun <T, R> execute(
        call: Call<T>,
        transform: (T) -> R
    ): Either<Failure, R> {
        return try {
            //отправляем синхронный запрос к серверу
            val response = call.execute()
            Timber.d("return data: ${response.body()}")
            when (response.isSucceed()) {
                //так как запрос был успешен данные обязаны придти в body, можно использовать !!
                true -> Either.Right(transform((response.body())!!))
                false -> Either.Left(response.parseError())
            }
        } catch (exception: Throwable) {
            Timber.d("Ошибка уровня приложения: ${exception.fillInStackTrace()}")
            //в случае когда возникла проблема с подключением появляется эта ошибка
            when (exception) {
                is SocketTimeoutException -> Either.Left(Failure.NetworkConnectionError)
                else -> Either.Left(Failure.ServerError)
            }
        }
    }
}

/** Функция проверят успешно ли прошёл запрос */
fun <T> Response<T>.isSucceed(): Boolean = isSuccessful && body() != null

/** Функция обработчик ошибки. Когда запрос прошёл но с бэка всё равно вернулась ошибка */
fun <T> Response<T>.parseError(): Failure {
    Timber.d("Error message: $this")
    return when (code()) {
        401 -> Failure.IncorrectLoginInfoError
        403 -> Failure.UnknownLoginError
        405 -> Failure.NotAllowedError
        429 -> Failure.TooManyRequestError
        204 -> Failure.NoContentError
        else -> Failure.ServerError
    }
}