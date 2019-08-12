package com.example.customtasks

import android.os.Build
import android.util.Log
import android.widget.Chronometer
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

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
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            startTime = current.format(formatter)
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            startTime = formatter.format(date)
        }
        taskInProgress = true
    }

    fun stop(){
        if (startTime.isNotEmpty()) {
            var minutes:Long = 0
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                val last = formatter.parse(startTime) as LocalDateTime
                val current = LocalDateTime.now()
                minutes = ChronoUnit.MINUTES.between(current, last)
            }
            else{
                val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                val last = formatter.parse(startTime)
                val current = Date()
                val timeUnit = TimeUnit.valueOf("dd.MM.yyyy HH:mm:ss")
                minutes = Duration.between(current.toInstant(), last.toInstant()).toMinutes()
                minutes = ChronoUnit.MINUTES.between(current, last)
            }
            duration += minutes
        }
        startTime = ""
    }

}