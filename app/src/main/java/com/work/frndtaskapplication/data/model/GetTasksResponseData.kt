package com.work.frndtaskapplication.data.model

import com.google.gson.annotations.SerializedName

data class GetTasksResponseData(
    @SerializedName("tasks")
    val taskData: List<TaskData>?
)