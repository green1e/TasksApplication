package com.work.frndtaskapplication.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.work.frndtaskapplication.R
import com.work.frndtaskapplication.data.ApiResult
import com.work.frndtaskapplication.ui.tasks.DeleteTaskListener
import com.work.frndtaskapplication.data.model.Date
import com.work.frndtaskapplication.data.network.USER_ID
import com.work.frndtaskapplication.databinding.FragmentCalendarBinding
import com.work.frndtaskapplication.dpToPx
import com.work.frndtaskapplication.getMonthFullText
import com.work.frndtaskapplication.isEqualTo
import com.work.frndtaskapplication.showToast
import com.work.frndtaskapplication.ui.tasks.TasksListItemDecorator
import com.work.frndtaskapplication.ui.tasks.DeleteTaskViewModel
import com.work.frndtaskapplication.ui.tasks.TasksAdapter
import com.work.frndtaskapplication.ui.tasks.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class CalendarFragment : Fragment(), DeleteTaskListener {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val tasksViewModel: TasksViewModel by activityViewModels()
    private val deleteTaskViewModel: DeleteTaskViewModel by viewModels()

    private var adapter = TasksAdapter(this, false)

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksViewModel.clickedDate = Date(
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.DAY_OF_MONTH).toString()
        )
        binding.rvSelectedDateTasks.adapter = adapter
        binding.rvSelectedDateTasks.addItemDecoration(
            TasksListItemDecorator(0, resources.dpToPx(16))
        )
        binding.calendarView.onDateClick = { clickedDate ->
            tasksViewModel.clickedDate = clickedDate
            setTasksList(clickedDate)
        }
        subscribeObservers()
    }

    private fun subscribeObservers() {
        tasksViewModel.tasks.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Error -> {}
                is ApiResult.Loading -> {}
                is ApiResult.Success -> {
                    tasksViewModel.clickedDate?.let { setTasksList(it) }
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

    private fun setTasksList(clickedDate: Date) {
        val tasks = tasksViewModel.tasks.value as ApiResult.Success
        val list = tasks.data?.taskData?.filter { taskData ->
            taskData.taskDetail.date.isEqualTo(clickedDate)
        }?.sortedBy { taskData ->
            taskData.taskId
        }
        setTasksTitle(clickedDate)
        adapter.submitList(list)
    }

    private fun setTasksTitle(date: Date) {
        binding.tvTasksTitle.text = getString(
            R.string.tasks_, getString(
                R.string.date_format,
                date.day,
                date.month.getMonthFullText(),
                date.year.toString()
            )
        )
    }

    override fun onDeleteTask(taskId: Long) {
        deleteTaskViewModel.deleteTask(USER_ID, taskId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}