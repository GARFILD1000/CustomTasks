package com.example.customtasks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class ItemsDatabase : RoomDatabase(){
    abstract fun itemDao() : ItemDao

    companion object {
        private var instance: ItemsDatabase? = null

        fun getInstance(context : Context): ItemsDatabase?{
            if (instance == null){
                synchronized(ItemsDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemsDatabase::class.java,
                        "items.db")
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