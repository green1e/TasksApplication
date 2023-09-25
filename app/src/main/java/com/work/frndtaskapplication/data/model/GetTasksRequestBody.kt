package com.work.frndtaskapplication.data.model

import com.google.gson.annotations.SerializedName

data class GetTasksRequestBody(
    @SerializedName("user_id")
    val userId: Long
)