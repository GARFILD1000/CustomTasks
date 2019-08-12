package com.example.customtasks

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item : Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item : Item)

    @Query("DELETE FROM items")
    fun deleteAllItems()

    @Query("SELECT * FROM items")
    fun getAllItems() : LiveData<MutableList<Item>>
}