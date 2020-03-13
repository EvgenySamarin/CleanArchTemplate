package com.example.cleanarchtemplate.data.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleanarchtemplate.data.cache.database.dao.ExampleDAO
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity

/**
 * Is responsible for create room database instance
 *
 * @author EvgenySamarin
 * @since 20200212 v2
 */
@Database(
    entities = [
        ExampleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun exampleDAO(): ExampleDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppRoomDatabase::class.java,
                        "room_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}