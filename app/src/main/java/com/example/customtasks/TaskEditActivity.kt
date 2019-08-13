package com.example.customtasks

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TaskEditActivity : AppCompatActivity(){
    companion object{
        const val ITEM_OBJECT = "taskObject"
        const val ITEM_NUMBER = "taskNumber"
    }

    var taskNumber = 0
    lateinit var task : Task

    val taskNameEditText by lazy(LazyThreadSafetyMode.NONE){
        findViewById<EditText>(R.id.taskNameEditText)
    }

    val taskDescEditText by lazy(LazyThreadSafetyMode.NONE){
        findViewById<EditText>(R.id.taskDescEditText)
    }

    val button by lazy(LazyThreadSafetyMode.NONE){
        findViewById<Button>(R.id.saveTaskButton)
    }

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_edit)
        task = intent.getSerializableExtra(ITEM_OBJECT) as Task
        taskNumber = intent.getIntExtra(ITEM_NUMBER, 0)
        taskNameEditText.setText(task.name)
        taskDescEditText.setText(task.data)
        supportActionBar!!.title = "Task Editing"
        button.setOnClickListener{v -> saveTask()}
    }

    private fun saveTask(){
        if (taskNameEditText.text.toString().isEmpty() ||
                taskDescEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Fill all info before saving", Toast.LENGTH_LONG).show()
        }
        else{
            task.name = taskNameEditText.text.toString()
            task.data = taskDescEditText.text.toString()
            intent.putExtra(ITEM_OBJECT, task)
            intent.putExtra(ITEM_NUMBER, taskNumber)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

