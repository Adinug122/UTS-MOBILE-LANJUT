package com.example.unscramble

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Words::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun WordsDao(): WordsDao


    companion object{
    var INSTANCE: AppDatabase?= null

        fun getInstance(applicationContext: Context): AppDatabase{
            return INSTANCE?: Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,"database-kata"
            ).build().also{
                INSTANCE = it
            }
        }
    }

}