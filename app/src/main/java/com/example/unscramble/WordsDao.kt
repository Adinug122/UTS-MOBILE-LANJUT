package com.example.unscramble

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordsDao {

@Query("SELECT * FROM words")
suspend fun getAllWords(): List<Words>

@Insert
suspend fun insert(vararg words: Words)

}