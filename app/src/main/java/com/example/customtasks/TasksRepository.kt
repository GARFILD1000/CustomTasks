package com.example.customtasks

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class TasksRepository(application : Application){
    private var taskDao : TaskDao
    private var tasks : LiveData<List<Task>>

    init{
        val database = TasksDatabase.getInstance(application)
        taskDao = database!!.taskDao()
        tasks = taskDao.getAllTasks()
    }

    fun insertTask (task : Task){
        doAsync{
            taskDao.insert(task)
        }.execute()
    }

    fun updateTask (task : Task){
        doAsync{
            taskDao.update(task)
        }.execute()
    }

    fun deleteTask (task : Task){
        doAsync{
            taskDao.delete(task)
        }.execute()
    }

    fun deleteAllTasks (){
        doAsync{
            taskDao.deleteAllTasks()
        }.execute()
    }

    fun getAllTasks(): LiveData<List<Task>>{
        return tasks
    }

    class doAsync(val databaseOperation: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            databaseOperation()
            return null
        }
    }
}