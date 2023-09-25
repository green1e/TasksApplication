package com.work.frndtaskapplication.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.work.frndtaskapplication.data.model.Date
import com.work.frndtaskapplication.databinding.FragmentCalendarDialogBinding
import java.util.Calendar

class CalendarDialogFragment : DialogFragment() {

    private var _binding: FragmentCalendarDialogBinding? = null
    private val binding get() = _binding!!

    var onDateClick: ((date: Date) -> Unit)? = null
    private var selectedDate: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val calendar = Calendar.getInstance()
        selectedDate = Date(
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.DAY_OF_MONTH).toString()
        )

        binding.calendarView.onDateClick = { selectedDate = it }

        binding.btnDone.setOnClickListener {
            selectedDate?.let { it1 -> onDateClick?.invoke(it1) }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "CalendarDialogFragment"

        fun show(fm: FragmentManager, block: ((date: Date) -> Unit)) {
            val prev = fm.findFragmentByTag(TAG)
            if (prev == null) {
                CalendarDialogFragment().apply {
                    onDateClick = block
                    show(fm, TAG)
                }
            }
        }
    }
}