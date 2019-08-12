package com.example.customtasks

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ItemsRepository(application : Application){
    private var itemDao : ItemDao
    private var items : LiveData<MutableList<Item>>

    init{
        val database = ItemsDatabase.getInstance(application)
        itemDao = database!!.itemDao()
        items = itemDao.getAllItems()
    }

    fun insertItem (item : Item){
        InsertItemAsyncTask(itemDao).execute(item)
    }

    fun updateItem (item : Item){
        UpdateItemAsyncTask(itemDao).execute(item)
    }

    fun deleteItem (item : Item){
        DeleteItemAsyncTask(itemDao).execute(item)
    }

    fun deleteAllItems (){
        DeleteAllItemsAsyncTask(itemDao).execute()
    }

    fun getAllItems(): LiveData<MutableList<Item>>{
        return items
    }

    private class InsertItemAsyncTask(private val itemDao: ItemDao) :
        AsyncTask<Item, Void, Void>() {
        override fun doInBackground(vararg items: Item): Void? {
            itemDao.insert(items[0])
            return null
        }
    }

    private class DeleteAllItemsAsyncTask(private val itemDao: ItemDao) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg voids: Void): Void? {
            itemDao.deleteAllItems()
            return null
        }
    }

    private class UpdateItemAsyncTask(private val itemDao: ItemDao) :
        AsyncTask<Item, Void, Void>() {
        override fun doInBackground(vararg items: Item): Void? {
            itemDao.update(items[0])
            return null
        }
    }

    private class DeleteItemAsyncTask(private val itemDao: ItemDao) :
        AsyncTask<Item, Void, Void>() {
        override fun doInBackground(vararg items: Item): Void? {
            itemDao.delete(items[0])
            return null
        }
    }

}