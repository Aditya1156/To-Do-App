package com.example.jaldikaro.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jaldikaro.model.Task

@Dao
interface TaskDao {

    // Insert Task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    // Update Task
    @Update
    suspend fun update(task: Task)

    // Delete Task
    @Delete
    suspend fun delete(task: Task)

    // Get All Tasks
    @Query("SELECT * FROM task_table ORDER BY dueDate ASC")
    fun getAllTasks(): LiveData<List<Task>>

    // Delete All Tasks
    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()
}
