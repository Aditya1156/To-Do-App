package com.example.jaldikaro.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jaldikaro.databinding.ActivityAddEditTaskBinding
import com.example.jaldikaro.model.Task
import java.text.SimpleDateFormat
import java.util.*

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTaskBinding
    private var task: Task? = null
    private var dueDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get Task from Intent (if editing)
        task = intent.getParcelableExtra("task")
        task?.let {
            populateTaskData(it)
        }

        binding.btnSaveTask.setOnClickListener {
            saveTask()
        }

        binding.etDueDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun populateTaskData(task: Task) {
        binding.etTaskName.setText(task.taskName)
        binding.etTaskType.setText(task.taskType)

        // Format Date to String if available
        task.dueDate?.let {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.etDueDate.setText(dateFormat.format(it))
            dueDate = it
        }
        binding.cbCompleted.isChecked = task.isCompleted
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                dueDate = selectedCalendar.time

                // Format and show selected date in EditText
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.etDueDate.setText(dateFormat.format(dueDate!!))
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun saveTask() {
        val taskName = binding.etTaskName.text.toString().trim()
        val taskType = binding.etTaskType.text.toString().trim()
        val isCompleted = binding.cbCompleted.isChecked

        if (taskName.isEmpty() || taskType.isEmpty()) {
            Toast.makeText(this, "Task Name and Type are required!", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedTask = Task(
            id = task?.id ?: 0,
            taskName = taskName,
            taskType = taskType,
            dueDate = dueDate,
            isCompleted = isCompleted
        )

        // Return the updated task to MainActivity
        val resultIntent = Intent().apply {
            putExtra("task_result", updatedTask)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
