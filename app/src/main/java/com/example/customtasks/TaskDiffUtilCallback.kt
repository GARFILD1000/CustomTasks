package com.example.customtasks

import androidx.recyclerview.widget.DiffUtil

class TaskDiffUtilCallback(val oldList: List<Task>, val newList: List<Task>) : DiffUtil.Callback(){

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        var isSame = true
        isSame = isSame and (oldList[oldItemPosition].name == newList[newItemPosition].name)
        isSame = isSame and (oldList[oldItemPosition].data == newList[newItemPosition].data)
        isSame = isSame and (oldList[oldItemPosition].duration == newList[newItemPosition].duration)
        return isSame
    }

}