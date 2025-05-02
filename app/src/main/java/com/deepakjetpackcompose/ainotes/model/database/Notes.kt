package com.deepakjetpackcompose.ainotes.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val title:String,
    val content:String,
    val timeStamp: Long
)