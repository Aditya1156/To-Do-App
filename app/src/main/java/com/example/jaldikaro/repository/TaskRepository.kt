package com.example.jaldikaro.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.jaldikaro.dao.TaskDao
import com.example.jaldikaro.database.TaskDatabase
import com.example.jaldikaro.model.Task

class TaskRepository(context: Context) {

    private val taskDao: TaskDao

    init {
        // Get the database instance and initialize taskDao
        val database = TaskDatabase.getDatabase(context)
        taskDao = database.taskDao()
    }

    // Get All Tasks
    fun getAllTasks(): LiveData<List<Task>> {
        return taskDao.getAllTasks()
    }

    // Insert Task
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    // Update Task
    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    // Delete Task
    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    // Delete All Tasks
    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }
}
