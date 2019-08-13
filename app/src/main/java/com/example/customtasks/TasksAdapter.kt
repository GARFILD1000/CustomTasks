package com.example.customtasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TasksAdapter: RecyclerView.Adapter<TasksAdapter.TasksHolder>() {
    private var tasks : MutableList<Task> = LinkedList()

    var onTaskEditClicked = fun(position: Int): Unit = null!!
    var onTaskStartClicked = fun(position: Int, isStarted: Boolean): Unit = null!!


    fun addTask(newTask : Task){
        tasks.add(newTask)
        notifyItemInserted(tasks.size-1)
    }

    fun removeAllTasks(){
        val previousSize = tasks.size
        tasks.clear()
        notifyItemRangeRemoved(0, previousSize)
    }

    fun removeTaskAt(position : Int){
        if(position < tasks.size) {
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getTask(position : Int) : Task{
        return tasks[position]
    }

    fun setTask(position : Int, task : Task){
        if(position < tasks.size){
            tasks[position] = task
            notifyItemChanged(position)
        }
    }

    fun updateTasks(newTasks: MutableList<Task>){
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_task_view, parent, false)
        return TasksHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TasksHolder, position: Int) {
        holder.bind(tasks[position])
    }

    inner class TasksHolder(private var view : View) : RecyclerView.ViewHolder(view){
        val nameTextView : TextView by lazy(LazyThreadSafetyMode.NONE){
            view.findViewById<TextView>(R.id.taskNameTextView)
        }

        val taskStartButton : CheckBox by lazy(LazyThreadSafetyMode.NONE){
            view.findViewById<CheckBox>(R.id.taskStartButton)
        }

        val taskEditButton : ImageButton by lazy(LazyThreadSafetyMode.NONE){
            view.findViewById<ImageButton>(R.id.editTaskButton)
        }

        init{
        }

        fun bind(task : Task ){
            nameTextView.setText(task.name)
            taskStartButton.setOnClickListener{view -> onTaskStartClicked(layoutPosition, taskStartButton.isChecked)}
            //val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener{buttonView, isChecked -> onTaskStartClicked(layoutPosition, isChecked)}
            taskStartButton.isChecked = task.startTime.isNotEmpty()
            //taskStartButton.setOnCheckedChangeListener(onCheckedChangeListener)
            taskEditButton.setOnClickListener{buttonView -> onTaskEditClicked(layoutPosition)}

        }
    }
}