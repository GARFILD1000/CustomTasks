package com.example.customtasks

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task : Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task : Task)

    @Query("DELETE FROM tasks")
    fun deleteAllTasks()

    @Query("SELECT * FROM tasks")
    fun getAllTasks() : LiveData<MutableList<Task>>
}