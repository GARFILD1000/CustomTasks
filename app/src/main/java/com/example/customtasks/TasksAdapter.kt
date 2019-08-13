package com.example.customtasks

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_task_view.view.*
import org.joda.time.Duration
import org.joda.time.Instant
import java.util.*

class TasksAdapter: RecyclerView.Adapter<TasksAdapter.TasksHolder>() {
    private var items: MutableList<Task> = LinkedList()

    lateinit var onTaskEditClicked:(position: Int)->Unit
    lateinit var onTaskStartClicked:(position: Int, isStarted: Boolean)->Unit

    fun addItem(newItem : Task){
        items.add(newItem)
        notifyItemInserted(items.size-1)
    }

    fun removeAllItems(){
        val previousSize = items.size
        items.clear()
        notifyItemRangeRemoved(0, previousSize)
    }

    fun removeItemAt(position : Int){
        if(position < items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position : Int) : Task{
        return items[position]
    }

    fun setItem(position : Int, task : Task){
        if(position < items.size){
            items[position] = task
            notifyItemChanged(position)
        }
    }

    fun setItems(newItems: List<Task>){
        var taskDiffUtilCallback = TaskDiffUtilCallback(items, newItems)
        var diffResult = DiffUtil.calculateDiff(taskDiffUtilCallback)
        items = LinkedList(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItems() : List<Task>{
        return items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_task_view, parent, false)
        return TasksHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TasksHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class TasksHolder(view : View) : RecyclerView.ViewHolder(view){
        val nameTextView = view.taskNameTextView
        val taskStartButton = view.taskStartButton
        val taskEditButton = view.editTaskButton
        val timeTextView = view.timeTextView

        fun bind(task : Task ){
            nameTextView.setText(task.name)
            taskStartButton.setOnClickListener{view -> onTaskStartClicked(layoutPosition, taskStartButton.isChecked)}
            taskStartButton.isChecked = task.startTime.isNotEmpty()
            taskEditButton.setOnClickListener{buttonView -> onTaskEditClicked(layoutPosition)}

            val durString = "${task.duration/60}:${task.duration%60}"
            timeTextView.text = durString
        }
    }
}