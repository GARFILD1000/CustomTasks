package com.example.customtasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ItemsViewModel(application : Application) : AndroidViewModel(application){
    val repository = ItemsRepository(application)
    var items = repository.getAllItems()

    fun getAll(): LiveData<MutableList<Item>>{
        return items
    }

    fun insert(item: Item){
        repository.insertItem(item)
    }

    fun delete(item: Item){
        repository.deleteItem(item)
    }

    fun update(item: Item){
        repository.updateItem(item)
    }

    fun deleteAll(){
        repository.deleteAllItems()
    }
}