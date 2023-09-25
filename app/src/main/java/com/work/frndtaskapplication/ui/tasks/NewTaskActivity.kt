package com.work.frndtaskapplication.ui.tasks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.work.frndtaskapplication.R
import com.work.frndtaskapplication.data.ApiResult
import com.work.frndtaskapplication.data.model.Date
import com.work.frndtaskapplication.data.model.TaskDetail
import com.work.frndtaskapplication.data.network.USER_ID
import com.work.frndtaskapplication.databinding.ActivityNewTaskBinding
import com.work.frndtaskapplication.getDate
import com.work.frndtaskapplication.getMonthFullText
import com.work.frndtaskapplication.getSerializable
import com.work.frndtaskapplication.showToast
import com.work.frndtaskapplication.ui.calendar.CalendarDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewTaskBinding

    private val tasksViewModel: TasksViewModel by viewModels()

    private var datePickerSelectedDate: Date? = null

    private val initialDate by lazy {
        intent.getSerializable<Date>(INITIAL_DATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
        setupView()
    }

    private fun subscribeObservers() {
        tasksViewModel.saveTask.observe(this) {
            when (it) {
                is ApiResult.Error -> {
                    showToast("Something went wrong")
                }

                is ApiResult.Loading -> {}
                is ApiResult.Success -> {
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra(TASK_ADDED_SUCCESS, true)
                    })
                    finish()
                }
            }
        }
    }

    private fun setupView() {
        if (initialDate == null) {
            binding.tvDate.setOnClickListener {
                CalendarDialogFragment.show(supportFragmentManager) { date ->
                    setDate(date)
                }
            }
        } else setDate(initialDate)
        binding.btnSaveTask.setOnClickListener {
            if (binding.etTitle.text.isNullOrBlank()) {
                showToast("Enter Task Title")
            } else if (binding.etDescription.text.isNullOrBlank()) {
                showToast("Enter Task Description")
            } else if (binding.tvDate.text.isNullOrBlank()) {
                showToast("Enter Task Date")
            } else {
                val date = datePickerSelectedDate ?: return@setOnClickListener
                tasksViewModel.saveTask(
                    USER_ID, TaskDetail(
                        date = date.getDate(),
                        title = binding.etTitle.text?.toString() ?: "",
                        description = binding.etDescription.text?.toString() ?: ""
                    )
                )
            }
        }
    }

    private fun setDate(date: Date?) {
        date ?: return
        datePickerSelectedDate = date
        binding.tvDate.text = getString(
            R.string.date_format,
            date.day,
            date.month.getMonthFullText(),
            date.year.toString()
        )
    }

    companion object {
        const val TASK_ADDED_SUCCESS = "TASK_ADDED_SUCCESS"
        private const val INITIAL_DATE = "INITIAL_DATE"

        fun getIntent(context: Context, date: Date? = null): Intent {
            return Intent(context, NewTaskActivity::class.java).apply {
                date?.let { putExtra(INITIAL_DATE, it) }
            }
        }
    }
}