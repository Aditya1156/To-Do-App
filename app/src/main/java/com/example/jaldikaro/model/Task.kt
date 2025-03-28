package com.example.jaldikaro.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskName: String,
    val taskType: String,
    val dueDate: Date?, // Correct Date type
    val isCompleted: Boolean = false
) : Parcelable
