package com.example.customtasks

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.joda.time.Duration
import org.joda.time.Instant
import java.io.Serializable

@Entity(tableName = "tasks")
class Task (): Serializable{

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var name : String = ""

    var data : String = ""

    var startTime : String = ""
    var duration : Long = 0

    constructor( name : String, data : String) : this() {
        this.name = name
        this.data = data
    }

    constructor(task : Task) : this(task.name, task.data){
        this.id = task.id
        this.duration = task.duration
        this.startTime = task.startTime
    }

    @Ignore
    lateinit var onTaskStart : (startTime:String, duration:Long)->Unit
    @Ignore
    lateinit var onTaskStop : (stopTime:String, duration:Long)->Unit

    fun trigger(){
        if (startTime.isNotEmpty()) {
            updateDuration()
            startTime = ""
            onTaskStop(Instant.now().toString(), duration)
        }
        else{
            val instant = Instant.now()
            startTime = instant.toString()
            onTaskStart(startTime, duration)
        }
    }

    fun updateDuration() : Boolean{
        var updated = false
        if (startTime.isNotEmpty()){
            val lastMoment : Instant = Instant.parse(startTime)
            val thisMoment : Instant = Instant.now()
            val difference = Duration.millis(thisMoment.millis - lastMoment.millis).toStandardSeconds().seconds
            updated = difference > 0
            duration += difference
            startTime = Instant.now().toString()
        }
        return updated
    }

}