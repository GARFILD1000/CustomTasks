package com.example.customtasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val CHANGE_ITEM_REQUEST = 1
        const val CREATE_ITEM_REQUEST = 2
    }

    lateinit var tasksViewModel : TasksViewModel
    lateinit var linearLayoutManager : LinearLayoutManager
    lateinit var tasksAdapter : TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        initToolbar()
        tasksViewModel = ViewModelProviders.of(this).get(TasksViewModel::class.java)
        tasksViewModel.getAll().observe(this,  Observer<List<Task>>{tasksAdapter.setItems(it)})
    }

    private fun initToolbar(){
        toolBar.inflateMenu(R.menu.toolbar_menu)
        toolBar.title = "Tasks"
        toolBar.setOnMenuItemClickListener{
                item ->
            when(item.itemId){
                R.id.addNewTask -> {
                    createNewTask()
                }
                R.id.updateTasksDurations -> {
                    updateTasksDurations()
                }
            }
            true
        }
    }

    private fun initRecyclerView(){
        tasksAdapter = TasksAdapter()
        tasksView.adapter = tasksAdapter
        linearLayoutManager = LinearLayoutManager(this)
        tasksView.layoutManager = linearLayoutManager

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTask(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(tasksView)
        tasksAdapter.onTaskEditClicked = {position : Int -> changeTask(position)}
        tasksAdapter.onTaskStartClicked = {position : Int, isStarted: Boolean ->
            updateTask(tasksAdapter.getItem(position), isStarted)}
    }

    private fun createNewTask(){
        val intent = Intent(this, TaskEditActivity::class.java)
        intent.putExtra(TaskEditActivity.ITEM_OBJECT, Task())
        intent.putExtra(TaskEditActivity.ITEM_NUMBER, tasksAdapter.itemCount)
        startActivityForResult(intent, CREATE_ITEM_REQUEST)
    }

    private fun changeTask(position : Int){
        val intent = Intent(this, TaskEditActivity::class.java)
        intent.putExtra(TaskEditActivity.ITEM_OBJECT, tasksAdapter.getItem(position))
        intent.putExtra(TaskEditActivity.ITEM_NUMBER, position)
        startActivityForResult(intent, CHANGE_ITEM_REQUEST)
    }

    private fun deleteTask(position : Int){
        tasksViewModel.delete(tasksAdapter.getItem(position))
    }

    private fun updateTask(task : Task, isStarted : Boolean){
        task.onTaskStop = {_, duration ->
            Toast.makeText(this, "This task take ${duration/60} minutes", Toast.LENGTH_LONG).show()
        }
        task.onTaskStart = {_, duration ->
            Toast.makeText(this, "Start task that already take ${duration/60} minutes", Toast.LENGTH_LONG).show()
        }
        task.trigger()
        tasksViewModel.update(task)
    }

    private fun updateTasksDurations(){
        for(i in 0 until tasksAdapter.itemCount){
            val task = Task(tasksAdapter.getItem(i))
            if (task.updateDuration()) {
                tasksViewModel.update(task)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHANGE_ITEM_REQUEST && resultCode == RESULT_OK) {
            val task = data!!.getSerializableExtra(TaskEditActivity.ITEM_OBJECT) as Task
            tasksViewModel.update(task)
        }
        if (requestCode == CREATE_ITEM_REQUEST && resultCode == RESULT_OK) {
            val task = data!!.getSerializableExtra(TaskEditActivity.ITEM_OBJECT) as Task
            tasksViewModel.insert(task)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tasks_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(task: MenuItem): Boolean {
        return when(task.itemId){
            R.id.addNewTask -> {
                createNewTask()
                true
            }
            R.id.deleteAllTasks -> {
                tasksViewModel.deleteAll()
                true
            }
            else -> {
                super.onOptionsItemSelected(task)
            }
        }
    }
}