package com.example.jaldikaro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jaldikaro.databinding.ItemTaskBinding
import com.example.jaldikaro.model.Task

class TaskAdapter(private val listener: OnTaskClickListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var tasks: List<Task> = emptyList()

    // Interface for Click Handling
    interface OnTaskClickListener {
        fun onTaskClick(task: Task)
        fun onTaskChecked(task: Task, isChecked: Boolean)
        fun onTaskLongClick(task: Task)
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        init {
            binding.root.setOnClickListener(this)
            binding.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    listener.onTaskChecked(task, isChecked)
                }
            }
            binding.root.setOnLongClickListener(this)
        }

        fun bind(task: Task) {
            binding.apply {
                tvTaskName.text = task.taskName
                tvTaskType.text = task.taskType
                tvDueDate.text = task.dueDate?.toString() ?: "No Due Date"
                cbCompleted.isChecked = task.isCompleted
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onTaskClick(tasks[position])
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onTaskLongClick(tasks[position])
            }
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    // Update the list of tasks using DiffUtil for better performance
    fun setTasks(newTaskList: List<Task>) {
        val diffCallback = TaskDiffCallback(tasks, newTaskList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        tasks = newTaskList
        diffResult.dispatchUpdatesTo(this)
    }

    // DiffUtil to update only changed items in RecyclerView
    class TaskDiffCallback(
        private val oldList: List<Task>,
        private val newList: List<Task>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
