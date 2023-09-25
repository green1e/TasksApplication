package com.work.frndtaskapplication.data.repository

import com.work.frndtaskapplication.data.ApiResult
import com.work.frndtaskapplication.data.model.DeleteTaskRequestBody
import com.work.frndtaskapplication.data.model.GenericResponseData
import com.work.frndtaskapplication.data.model.GetTasksRequestBody
import com.work.frndtaskapplication.data.model.GetTasksResponseData
import com.work.frndtaskapplication.data.model.SaveTaskRequestBody
import com.work.frndtaskapplication.data.model.TaskDetail
import com.work.frndtaskapplication.data.network.TasksService
import com.work.frndtaskapplication.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(private val tasksService: TasksService) :
    TasksRepository {
    override fun getTasks(userId: Long): Flow<ApiResult<GetTasksResponseData?>> {
        return safeApiCall {
            tasksService.getTasks(GetTasksRequestBody(userId))
        }
    }

    override fun saveTask(userId: Long, task: TaskDetail): Flow<ApiResult<GenericResponseData?>> {
        return safeApiCall {
            tasksService.saveTask(SaveTaskRequestBody(userId, task))
        }
    }

    override fun deleteTask(userId: Long, taskId: Long): Flow<ApiResult<GenericResponseData?>> {
        return safeApiCall {
            tasksService.deleteTask(DeleteTaskRequestBody(userId, taskId))
        }
    }
}