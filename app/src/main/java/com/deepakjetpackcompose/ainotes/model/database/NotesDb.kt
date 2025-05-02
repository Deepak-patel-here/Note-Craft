package com.deepakjetpackcompose.ainotes.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1)
abstract class NotesDb: RoomDatabase() {
    abstract fun getDao(): NotesDao

    companion object{
        @Volatile
        private var INSTANCE: NotesDb?=null

        fun getDb(context: Context): NotesDb{
            return INSTANCE?:synchronized (this){
                val instance= Room.databaseBuilder(
                    context=context.applicationContext,
                    NotesDb::class.java,
                    "notes_database"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}