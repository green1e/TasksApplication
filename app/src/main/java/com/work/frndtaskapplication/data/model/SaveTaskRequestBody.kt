package com.work.frndtaskapplication.data.model

import com.google.gson.annotations.SerializedName

data class SaveTaskRequestBody(
    @SerializedName("user_id")
    private val userId: Long,
    private val task: TaskDetail
)