package com.work.frndtaskapplication.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.work.frndtaskapplication.data.model.Date
import com.work.frndtaskapplication.databinding.ItemMonthViewBinding
import java.util.Calendar

class MonthAdapter(
    var dates: MutableList<Date>,
    private var onDateClick: DateClickListener
) : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {

    private val calendar = Calendar.getInstance()
    private var previousClickedDate: Date = Date(
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.DAY_OF_MONTH).toString()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
            ItemMonthViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(dates[position])
    }

    inner class MonthViewHolder(binding: ItemMonthViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var adapter = DayAdapter(
            onDateClick = object : DateClickListener {
                override fun onClick(date: Date) {
                    if (date.month != previousClickedDate.month || date.year != previousClickedDate.year) {
                        val index = dates.indexOfFirst {
                            it.month == previousClickedDate.month && it.year == previousClickedDate.year
                        }
                        previousClickedDate = date
                        notifyItemChanged(index)
                    }
                    previousClickedDate = date
                    onDateClick.onClick(date)
                }
            }
        )

        init {
            binding.rvCalendar.adapter = adapter
        }

        fun bind(monthYear: Date) {
            adapter.setDays(getDaysArray(monthYear), monthYear)
            adapter.previousClickedDate = previousClickedDate.copy()
        }

        private fun getDaysArray(monthYear: Date): MutableList<String> {
            val days = mutableListOf<String>()
            val calendar = Calendar.getInstance()
            calendar.set(monthYear.year, monthYear.month, 1)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            var currentDay = 1
            for (i in 1..42) {
                if (i >= dayOfWeek && currentDay <= maxDay) {
                    days.add(currentDay.toString())
                    currentDay++
                } else days.add("")
            }
            return days
        }
    }
}