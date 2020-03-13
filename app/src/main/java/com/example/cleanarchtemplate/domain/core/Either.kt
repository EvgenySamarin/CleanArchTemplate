package com.example.cleanarchtemplate.domain.core

/**
 * Это очень хитрая штука из функционального программирования, по факту может в себе хранить какой-то
 * класс символизирующий ошибку и какой-то класс символизирующий успех. Нужна для ручной обработки
 * каких-либо ошибок
 *
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * Functional Programming Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * Эта реализация обработки довольно популярная среди функциональных программистов. Класс нужен,
 * для передачи одного из двух возможных типов данных, неизвестного в момент компиляции, но
 * известного в момент выполнения.
 *
 * __Пример__: Сетевой запрос может возвращать как данные, так и ошибку. Either компонует их,
 * что помогает обработать их вместе с меньшим количеством кода.
 * @see Left
 * @see Right
 * @see <a href="http://oneeyedmen.com/failure-is-not-an-option-part-1.html">
 *     Functional Error Handling in Kotlin</a>
 *
 * @author EvgenySamarin
 * @since 20190905 v1
 */
sealed class Either<out Failure, out Success> {
    /**
     * Represents the left side of [Either] class which by convention is a "Failure".
     * Может содержать в себе только левую часть Either
     */
    data class Left<out Failure>(val failure: Failure) : Either<Failure, Nothing>()

    /**
     * Represents the right side of [Either] class which by convention is a "Success".
     * Может содержать в себе только правую часть Either.
     */
    data class Right<out Success>(val success: Success) : Either<Nothing, Success>()

    val isRight get() = this is Right<Success> //определён getter для поля isRight
    val isLeft get() = this is Left<Failure>

    /** отбрасывает правую часть Either приравнивая её к Nothing */
    fun <Failure> left(failure: Failure) = Left(failure)

    /** отбрасывает левую часть Either приравнивая её к Nothing */
    fun <Success> right(success: Success) = Right(success)

    /**
     * Выполняет одну из двух переданных функций высшего порядка переданных в параметре
     * @param functionLeft функция, которая будет выполнена в случае, если объект у которого
     * вызываем either: is Left
     * @param functionRight функция, которая будет выполнена в случае, если объект у которого
     * вызываем either: is Right
     * @return Возвращает базовый класс для всех Kotlin объектов: Any
     */
    fun either(
        functionLeft: (Failure) -> Any,
        functionRight: (Success) -> Any
    ): Any =
        when (this) {
            is Left -> functionLeft(failure)
            is Right -> functionRight(success)
        }
}

/**
 * Выполняет переданную в параметре функцию f, которая проивзодит какие-то действия с использованием
 * параметра B и возвращающую C. На выходе выдаёт функцию принимающую A и возвращающую C
 */
fun <A, B, C> ((A) -> B).compose(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * выполняет преобразование fn.
 * если объект Either является типом L (error) – возвращает его без изменений;
 * если объект Either является типом R (right) – с помощью переданной ф-ции высшего порядка
 * (fn: (R) -> Either<L, T>) подменяет исходный Either другим Either, преобразовывая его содержимое
 * (R)
 * @return Either<L, T>, где T – преобразованный R.
 */
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> {
    return when (this) {
        is Either.Left -> Either.Left(failure)
        is Either.Right -> fn(success)
    }
}

/**
 * выполнят преобразование fn.
 * если объект Either является типом L (error) – возвращает его без изменений;
 * если объект Either является типом R (success) – возвращает преобразованный с помощью переданной
 * ф-ции высшего порядка(fn: (R) -> (T) объект типа R.
 * @return Возвращает Either<L, T>, где T – преобразованный R.
 */
fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> {
    return this.flatMap(fn.compose(::right))
}

/** выполняет функию над объектом, когда она R  */
fun <L, R> Either<L, R>.onNext(fn: (R) -> Unit): Either<L, R> {
    this.flatMap(fn.compose(::right))
    return this
}