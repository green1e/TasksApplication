package com.work.frndtaskapplication.data

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    class Error<T> : ApiResult<T>()
    class Loading<T> : ApiResult<T>()
}