package com.example.customtasks

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class TasksRepository(application : Application){
    private var taskDao : TaskDao
    private var tasks : LiveData<MutableList<Task>>

    init{
        val database = TasksDatabase.getInstance(application)
        taskDao = database!!.taskDao()
        tasks = taskDao.getAllTasks()
    }

    fun insertTask (task : Task){
        InsertTaskAsyncTask(taskDao).execute(task)
    }

    fun updateTask (task : Task){
        UpdateTaskAsyncTask(taskDao).execute(task)
    }

    fun deleteTask (task : Task){
        DeleteTaskAsyncTask(taskDao).execute(task)
    }

    fun deleteAllTasks (){
        DeleteAllTasksAsyncTask(taskDao).execute()
    }

    fun getAllTasks(): LiveData<MutableList<Task>>{
        return tasks
    }

    private class InsertTaskAsyncTask(private val taskDao: TaskDao) :
        AsyncTask<Task, Void, Void>() {
        override fun doInBackground(vararg tasks: Task): Void? {
            taskDao.insert(tasks[0])
            return null
        }
    }

    private class DeleteAllTasksAsyncTask(private val taskDao: TaskDao) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg voids: Void): Void? {
            taskDao.deleteAllTasks()
            return null
        }
    }

    private class UpdateTaskAsyncTask(private val taskDao: TaskDao) :
        AsyncTask<Task, Void, Void>() {
        override fun doInBackground(vararg tasks: Task): Void? {
            taskDao.update(tasks[0])
            return null
        }
    }

    private class DeleteTaskAsyncTask(private val taskDao: TaskDao) :
        AsyncTask<Task, Void, Void>() {
        override fun doInBackground(vararg tasks: Task): Void? {
            taskDao.delete(tasks[0])
            return null
        }
    }

}