package com.example.customtasks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.*

@Database(entities = [Task::class], version = 1)
abstract class TasksDatabase : RoomDatabase(){
    abstract fun taskDao() : TaskDao

    companion object {
        private var instance: TasksDatabase? = null

        fun getInstance(context : Context): TasksDatabase?{
            if (instance == null){
                synchronized(TasksDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TasksDatabase::class.java,
                        "tasks.db")
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }
}