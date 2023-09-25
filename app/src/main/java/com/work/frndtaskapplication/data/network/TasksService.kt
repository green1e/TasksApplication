package com.work.frndtaskapplication.data.network

import com.work.frndtaskapplication.data.model.DeleteTaskRequestBody
import com.work.frndtaskapplication.data.model.GenericResponseData
import com.work.frndtaskapplication.data.model.GetTasksRequestBody
import com.work.frndtaskapplication.data.model.GetTasksResponseData
import com.work.frndtaskapplication.data.model.SaveTaskRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

const val USER_ID = 18355L

interface TasksService {
    @POST("getCalendarTaskList")
    suspend fun getTasks(@Body body: GetTasksRequestBody): Response<GetTasksResponseData>

    @POST("storeCalendarTask")
    suspend fun saveTask(@Body body: SaveTaskRequestBody): Response<GenericResponseData>

    @POST("deleteCalendarTask")
    suspend fun deleteTask(@Body body: DeleteTaskRequestBody): Response<GenericResponseData>
}