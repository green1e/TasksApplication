package com.work.frndtaskapplication.data.model

import com.google.gson.annotations.SerializedName

data class TaskData(
    @SerializedName("task_id")
    val taskId: Long,
    @SerializedName("task_detail")
    val taskDetail: TaskDetail
)