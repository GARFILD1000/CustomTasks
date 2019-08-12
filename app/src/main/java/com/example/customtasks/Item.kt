package com.example.customtasks

import android.os.Build
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "items")
class Item (): Serializable{

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var name : String = ""

    var data : String = ""

    var startTime : String = ""
    var duration : Long = 0
    var taskInProgress : Boolean = false

    constructor( name : String, data : String) : this() {
        this.name = name
        this.data = data
    }

    fun start(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val last = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
            var answer: String =  current.format(formatter)
        } else {
            var date = Date();
            date.
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            val answer: String = formatter.format(date)
        }
        startTime =
    }

    fun stop(){

    }

}