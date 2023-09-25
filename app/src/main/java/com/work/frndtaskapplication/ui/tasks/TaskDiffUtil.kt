package com.work.frndtaskapplication.ui.tasks

import androidx.recyclerview.widget.DiffUtil
import com.work.frndtaskapplication.data.model.TaskData

class TaskDiffUtil : DiffUtil.ItemCallback<TaskData>() {
    override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem.taskId == newItem.taskId
    }
}