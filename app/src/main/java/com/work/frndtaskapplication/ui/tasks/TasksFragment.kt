package com.work.frndtaskapplication.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.work.frndtaskapplication.data.ApiResult
import com.work.frndtaskapplication.data.network.USER_ID
import com.work.frndtaskapplication.databinding.FragmentTasksBinding
import com.work.frndtaskapplication.dpToPx
import com.work.frndtaskapplication.toYearMonthDay
import com.work.frndtaskapplication.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(), DeleteTaskListener {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val tasksViewModel: TasksViewModel by activityViewModels()
    private val deleteTaskViewModel: DeleteTaskViewModel by viewModels()

    private var adapter = TasksAdapter(this, true)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksViewModel.clickedDate = null
        binding.rvTasks.also {
            it.adapter = adapter
            it.addItemDecoration(TasksListItemDecorator(0, resources.dpToPx(16)))
        }
        subscribeObservers()
        tasksViewModel.getTasks(USER_ID)
    }

    private fun subscribeObservers() {
        tasksViewModel.tasks.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Loading -> {}
                is ApiResult.Error -> {}
                is ApiResult.Success -> {
                    if (it.data?.taskData.isNullOrEmpty()) {
                        binding.tvEmptyTasks.isVisible = true
                        binding.rvTasks.isVisible = false
                    } else {
                        binding.tvEmptyTasks.isVisible = false
                        binding.rvTasks.isVisible = true
                        val result = it.data?.taskData?.sortedBy { taskData ->
                            taskData.taskDetail.date.toYearMonthDay()
                        }
                        adapter.submitList(result) {
                            binding.rvTasks.post {
                                binding.rvTasks.invalidateItemDecorations()
                            }
                        }
                    }
                }
            }
        }
        deleteTaskViewModel.deleteTask.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Loading -> {}
                is ApiResult.Error -> {
                    context.showToast("Something went wrong")
                }

                is ApiResult.Success -> {
                    tasksViewModel.getTasks(USER_ID)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteTask(taskId: Long) {
        deleteTaskViewModel.deleteTask(USER_ID, taskId)
    }
}