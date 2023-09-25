package com.work.frndtaskapplication.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.frndtaskapplication.data.ApiResult
import com.work.frndtaskapplication.data.model.GenericResponseData
import com.work.frndtaskapplication.data.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteTaskViewModel @Inject constructor(private val tasksRepository: TasksRepository) :
    ViewModel() {

    private val _deleteTask = MutableLiveData<ApiResult<GenericResponseData?>>()
    val deleteTask: LiveData<ApiResult<GenericResponseData?>> get() = _deleteTask

    fun deleteTask(userId: Long, taskId: Long) {
        viewModelScope.launch {
            tasksRepository.deleteTask(userId, taskId).collect {
                _deleteTask.value = it
            }
        }
    }
}