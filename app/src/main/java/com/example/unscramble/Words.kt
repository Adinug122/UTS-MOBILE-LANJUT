package com.example.unscramble

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "words")
data class Words(

    @field:SerializedName("kata")
    val kata: String? = null,

    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("index")
    val index: Int = 0
)