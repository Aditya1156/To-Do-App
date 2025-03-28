package com.example.jaldikaro.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jaldikaro.adapter.TaskAdapter
import com.example.jaldikaro.databinding.ActivityMainBinding
import com.example.jaldikaro.model.Task
import com.example.jaldikaro.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), TaskAdapter.OnTaskClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private val taskViewModel: TaskViewModel by viewModels()

    // New API to handle Add/Edit Task Results
    private val taskResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = result.data?.getParcelableExtra<Task>("task_result")
            task?.let {
                if (it.id == 0) {
                    taskViewModel.insertTask(it)
                    Snackbar.make(binding.root, "Task Added!", Snackbar.LENGTH_SHORT).show()
                } else {
                    taskViewModel.updateTask(it)
                    Snackbar.make(binding.root, "Task Updated!", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        setupRecyclerView()

        // Observe Task Data from ViewModel
        taskViewModel.allTasks.observe(this) { tasks ->
            tasks?.let {
                if (it.isNotEmpty()) {
                    taskAdapter.setTasks(it)
                } else {
                    showEmptyState()
                }
            }
        }

        // Handle Floating Action Button Click to Add Task
        binding.fabAddTask.setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            taskResultLauncher.launch(intent)
        }
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(this)
        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }
    }

    // Handle Task Click to Show Details
    override fun onTaskClick(task: Task) {
        Toast.makeText(this, "Clicked: ${task.taskName}", Toast.LENGTH_SHORT).show()
    }

    // Handle Task Checked to Update Completion Status
    override fun onTaskChecked(task: Task, isChecked: Boolean) {
        taskViewModel.updateTask(task.copy(isCompleted = isChecked))
        val statusMessage = if (isChecked) "Task Completed!" else "Task Marked Incomplete."
        Snackbar.make(binding.root, statusMessage, Snackbar.LENGTH_SHORT).show()
    }

    // Handle Task Long Click to Edit/Delete Task
    override fun onTaskLongClick(task: Task) {
        showDeleteOrEditDialog(task)
    }

    // Show Edit or Delete Dialog
    private fun showDeleteOrEditDialog(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Edit or Delete Task")
            .setMessage("What would you like to do?")
            .setPositiveButton("Edit") { _, _ ->
                val intent = Intent(this, AddEditTaskActivity::class.java).apply {
                    putExtra("task", task)
                }
                taskResultLauncher.launch(intent)
            }
            .setNegativeButton("Delete") { _, _ ->
                taskViewModel.deleteTask(task)
                Snackbar.make(binding.root, "Task Deleted!", Snackbar.LENGTH_SHORT).show()
            }
            .setNeutralButton("Cancel", null)
            .show()
    }

    // Show Empty State Message
    private fun showEmptyState() {
        Snackbar.make(binding.root, "No tasks available. Add some tasks!", Snackbar.LENGTH_LONG)
            .show()
    }

    companion object {
        const val ADD_TASK_REQUEST = 1
        const val EDIT_TASK_REQUEST = 2
    }
}
