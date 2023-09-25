package com.work.frndtaskapplication.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.work.frndtaskapplication.R
import com.work.frndtaskapplication.data.model.Date
import com.work.frndtaskapplication.databinding.ItemCalendarViewDayBinding
import java.util.Calendar

class DayAdapter(
    private var days: MutableList<String>? = null,
    private var monthYear: Date? = null,
    private var onDateClick: DateClickListener?
) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    private val calendar = Calendar.getInstance()
    var previousClickedDate: Date = Date(
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.DAY_OF_MONTH).toString()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            ItemCalendarViewDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return days?.size ?: 0
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(days?.getOrNull(position))
    }

    fun setDays(days: MutableList<String>?, monthYear: Date) {
        this.days = days
        this.monthYear = monthYear
        notifyDataSetChanged()
    }

    inner class DayViewHolder(private val binding: ItemCalendarViewDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(day: String?) {
            if (day.isNullOrBlank()) return
            if (monthYear == null) return
            binding.tvDay.apply {
                text = day
                setBackgroundResource(0)

                if (monthYear?.month == calendar.get(Calendar.MONTH)
                    && monthYear?.year == calendar.get(Calendar.YEAR)
                    && day == calendar.get(Calendar.DAY_OF_MONTH).toString()
                ) {
                    setTextColor(resources.getColor(R.color.teal_500, null))
                } else setTextColor(resources.getColor(R.color.black, null))
                setOnClickListener {
                    if (monthYear?.month == previousClickedDate.month
                        && monthYear?.year == previousClickedDate.year
                    ) {
                        if (day == previousClickedDate.day) return@setOnClickListener
                        else days?.indexOf(previousClickedDate.day)?.let { it1 -> notifyItemChanged(it1) }
                    }
                    setBackgroundResource(R.drawable.bg_circle)
                    previousClickedDate.also {
                        it.day = day
                        it.month = monthYear?.month ?: 0
                        it.year = monthYear?.year ?: 0
                    }
                    onDateClick?.onClick(previousClickedDate)
                }
            }
        }
    }
}