package com.work.frndtaskapplication.data.model

import com.google.gson.annotations.SerializedName

data class DeleteTaskRequestBody(
    @SerializedName("user_id")
    private val userId: Long,
    @SerializedName("task_id")
    private val taskId: Long
)