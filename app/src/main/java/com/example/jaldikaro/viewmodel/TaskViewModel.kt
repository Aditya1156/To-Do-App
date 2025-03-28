package com.example.jaldikaro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.jaldikaro.model.Task
import com.example.jaldikaro.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        repository = TaskRepository(application.applicationContext)
        allTasks = repository.getAllTasks()
    }

    // Insert Task
    fun insertTask(task: Task) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.insert(task)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update Task
    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.update(task)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Delete Task
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.delete(task)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Delete All Tasks
    fun deleteAllTasks() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.deleteAllTasks()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
