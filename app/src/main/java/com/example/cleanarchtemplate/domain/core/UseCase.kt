package com.example.cleanarchtemplate.domain.core

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/**
 * Описывает общую логику работы всех USE-Cases (Событий) (Эту штуку ещё называют Interactor).
 * Выполняет работу в фоновом потоке с помощью kotlin coroutines, и передаёт результат в UI поток.
 *
 * @param Params тип класса-оболочки, хранящей параметры для выполнения функции
 * @param Type тип возвращаемого объекта с данными
 *
 * @author EvgenySamarin
 * @since 20190905 v1
 */
abstract class UseCase<out Type, in Params> {
    //создает объект фонового контекста для выполнения работы корутин.
    var backgroundContext: CoroutineContext = Dispatchers.IO
    //создает объект UI контекста для выполнения работ корутин.
    var foregroundContext: CoroutineContext = Dispatchers.Main
    //создает объект, используемый для инициирования и отмены работы.
    private var parentJob: Job = Job()

    /**
     * Абстрактная функция выполнения.  Все функции запускаемые внутри корутины обязаны иметь
     * оператор suspend (приостанавливаемый)
     *
     * @param params набор параметров переданных в конструкторе экземпляра UseCase
     *
     * @return Возвращает ошибку или тип, указанный при создании экземпляра UseCase.
     */
    abstract suspend fun run(params: Params): Either<Failure, Type>

    /**
     * Функция запускает корутину и выполняет run в фоновом потоке.
     *
     * Ключевое слово operator используется для перегрузки операторов в ЯВУ Kotlin в частности
     * здесь описан оператор: a.invoke() -> a().
     *
     * Т.е. вызвать эту функцию можно при помощи вызова: UseCase(params, onResult)
     *
     * @see <a href="https://kotlinlang.ru/docs/reference/operator-overloading.html">
     *     Перегрузка операторов в Kotlin</a>
     */
    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        unsubscribe()
        parentJob = Job()

        CoroutineScope(foregroundContext + parentJob).launch {
            val result = withContext(backgroundContext) {
                run(params)
            }
            onResult(result)
        }
    }

    /**
     * Функция отписки от работы. Отменяет выполнение корутины, знает о существовании корутины, т.к.
     * в корутине указана ссылка на Job
     */
    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }
}
