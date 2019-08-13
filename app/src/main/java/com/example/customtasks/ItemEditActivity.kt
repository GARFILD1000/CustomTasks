package com.example.customtasks

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ItemEditActivity : AppCompatActivity(){
    companion object{
        const val ITEM_OBJECT = "itemObject"
        const val ITEM_NUMBER = "itemNumber"
    }

    var itemNumber = 0
    lateinit var item : Item

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
        setContentView(R.layout.activity_item_edit)
        item = intent.getSerializableExtra(ITEM_OBJECT) as Item
        itemNumber = intent.getIntExtra(ITEM_NUMBER, 0)
        taskNameEditText.setText(item.name)
        taskDescEditText.setText(item.data)
        supportActionBar!!.title = "Task Editing"
        button.setOnClickListener{v -> saveItem()}
    }

    private fun saveItem(){
        if (taskNameEditText.text.toString().isEmpty() ||
                taskDescEditText.text.toString().isEmpty()){
            Toast.makeText(this,"Fill all info before saving", Toast.LENGTH_LONG).show()
        }
        else{
            item.name = taskNameEditText.text.toString()
            item.data = taskDescEditText.text.toString()
            intent.putExtra(ITEM_OBJECT, item)
            intent.putExtra(ITEM_NUMBER, itemNumber)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

