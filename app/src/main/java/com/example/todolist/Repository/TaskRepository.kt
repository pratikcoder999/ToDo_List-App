package com.example.todolist.Repository

import com.example.todolist.Data.RoomDB.TaskDao
import com.example.todolist.Data.RoomDB.TaskItem
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<TaskItem>> {
        return taskDao.getAllTasks()
    }

    suspend fun insertTask(task: TaskItem) = taskDao.insertTask(task)

    suspend fun updateTask(task: TaskItem) = taskDao.update(task)

    suspend fun deleteTask(task: TaskItem) = taskDao.delete(task)
}