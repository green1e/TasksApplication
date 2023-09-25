package com.work.frndtaskapplication.data.repository

import com.work.frndtaskapplication.data.ApiResult
import com.work.frndtaskapplication.data.model.GenericResponseData
import com.work.frndtaskapplication.data.model.GetTasksResponseData
import com.work.frndtaskapplication.data.model.TaskDetail
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks(userId: Long): Flow<ApiResult<GetTasksResponseData?>>
    fun saveTask(userId: Long, task: TaskDetail): Flow<ApiResult<GenericResponseData?>>
    fun deleteTask(userId: Long, taskId: Long): Flow<ApiResult<GenericResponseData?>>
}