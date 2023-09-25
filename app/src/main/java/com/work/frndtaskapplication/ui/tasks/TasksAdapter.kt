package com.work.frndtaskapplication.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.work.frndtaskapplication.data.model.TaskData
import com.work.frndtaskapplication.databinding.ItemTaskBinding
import com.work.frndtaskapplication.getDayMonthYear

class TasksAdapter(
    private val deleteTaskListener: DeleteTaskListener,
    private val showTaskDate: Boolean
) : ListAdapter<TaskData, TasksAdapter.TaskViewHolder>(TaskDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList.getOrNull(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TaskData?) {
            data ?: return
            val taskDetail = data.taskDetail
            binding.tvTaskTitle.text = taskDetail.title
            binding.tvTaskDescription.text = taskDetail.description
            binding.ivDeleteTask.setOnClickListener {
                deleteTaskListener.onDeleteTask(data.taskId)
            }
            if (showTaskDate) {
                binding.tvDate.isVisible = true
                binding.tvDate.text = taskDetail.date.getDayMonthYear()
            } else binding.tvDate.isVisible = false
        }
    }
}