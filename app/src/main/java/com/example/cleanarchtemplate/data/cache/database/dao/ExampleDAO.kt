package com.example.cleanarchtemplate.data.cache.database.dao

import androidx.annotation.Keep
import androidx.room.*
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity

/**
 * DAO Object to interact example data and different screen
 *
 * @author EvgenySamarin
 * @since 20200212 v1
 */
@Dao
@Keep
interface ExampleDAO {
    /**
     * Добавление элемента в БД
     * - @Insert аннотация - сообщает Room что функция предназначается для вставки. В случае
     * возникновения конфликта при вставке строка будет заменена на вставляемую
     *
     * @param item добавляемый объект
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExampleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(item: List<ExampleEntity>)

    /**
     * Удаление элемента из БД
     * - @Delete аннотация - сообщает Room что функция предназначена для удаления
     *
     * @param item удаляемый объект
     */
    @Delete
    suspend fun delete(item: ExampleEntity)

    /**
     * Удаление всех элементов из таблицы
     * - @Query аннотация - сообщает Room что функция имеет произвольный SQL запрос
     */
    @Query("DELETE FROM example")
    suspend fun deleteAll()

    /**
     * Получение всех элементов из БД
     * - @Query аннотация - сообщает Room что функция имеет произвольный SQL запрос
     *
     * @return список всех классов записанный в БД
     */
    @Query("SELECT * FROM example")
    suspend fun getAllItem(): List<ExampleEntity>
}