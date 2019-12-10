package com.example.anihub

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.anihub.ui.Converters
import com.example.anihub.ui.anime.AnimeDao
import com.example.anihub.ui.anime.AnimeModel


@Database(entities = [AnimeModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AnihubDB : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        @Volatile private var INSTANCE: AnihubDB? = null

        fun getDatabase(context: Context): AnihubDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnihubDB::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}