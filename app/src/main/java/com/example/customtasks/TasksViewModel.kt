package com.example.customtasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TasksViewModel(application : Application) : AndroidViewModel(application){
    val repository = TasksRepository(application)
    var tasks = repository.getAllTasks()

    fun getAll(): LiveData<MutableList<Task>>{
        return tasks
    }

    fun insert(task: Task){
        repository.insertTask(task)
    }

    fun delete(task: Task){
        repository.deleteTask(task)
    }

    fun update(task: Task){
        repository.updateTask(task)
    }

    fun deleteAll(){
        repository.deleteAllTasks()
    }
}