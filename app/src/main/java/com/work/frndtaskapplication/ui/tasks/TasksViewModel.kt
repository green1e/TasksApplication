package com.work.frndtaskapplication.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.frndtaskapplication.data.ApiResult
import com.work.frndtaskapplication.data.model.GenericResponseData
import com.work.frndtaskapplication.data.model.GetTasksResponseData
import com.work.frndtaskapplication.data.model.Date
import com.work.frndtaskapplication.data.model.TaskDetail
import com.work.frndtaskapplication.data.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val tasksRepository: TasksRepository) :
    ViewModel() {

    var clickedDate: Date? = null

    private val _tasks = MutableLiveData<ApiResult<GetTasksResponseData?>>()
    val tasks: LiveData<ApiResult<GetTasksResponseData?>> get() = _tasks

    fun getTasks(userId: Long) {
        viewModelScope.launch {
            tasksRepository.getTasks(userId).collect {
                _tasks.value = it
            }
        }
    }

    private val _saveTask = MutableLiveData<ApiResult<GenericResponseData?>>()
    val saveTask: LiveData<ApiResult<GenericResponseData?>> get() = _saveTask

    fun saveTask(userId: Long, task: TaskDetail) {
        viewModelScope.launch {
            tasksRepository.saveTask(userId, task).collect {
                _saveTask.value = it
            }
        }
    }
}