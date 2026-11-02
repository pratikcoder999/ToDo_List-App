package com.example.todolist.Data.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val taskName: String,
    val isDone: Boolean = false
)
