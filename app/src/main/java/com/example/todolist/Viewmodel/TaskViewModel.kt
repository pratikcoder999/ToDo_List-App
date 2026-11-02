package com.example.todolist.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.Repository.TaskRepository
import com.example.todolist.Data.RoomDB.TaskDatabase
import com.example.todolist.Data.RoomDB.TaskItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val dao = TaskDatabase.getDatabase(application).taskDao()
    private val repository =  TaskRepository(dao)

    val allTasks: StateFlow<List<TaskItem>> = repository.getAllTasks()  // Using StateFlow keeps live data and ui in sync.
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addTask(task: TaskItem) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: TaskItem) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: TaskItem) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

}