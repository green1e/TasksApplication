package com.work.frndtaskapplication.ui.calendar

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.work.frndtaskapplication.data.model.Date
import com.work.frndtaskapplication.databinding.ViewCalendarBinding
import com.work.frndtaskapplication.getMonthFullText
import java.util.Calendar

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), DateClickListener {

    private var binding: ViewCalendarBinding

    private var monthAdapter: MonthAdapter? = null

    private val currentPosition get() = binding.vpCalendar.currentItem

    var onDateClick: ((date: Date) -> Unit)? = null

    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        binding = ViewCalendarBinding.inflate(LayoutInflater.from(context), this, true)
        setupView()
    }

    private fun setupView() {
        binding.vpCalendar.apply {
            offscreenPageLimit = 1
            (getChildAt(0) as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
            monthAdapter = MonthAdapter(getDateList().toMutableList(), this@CalendarView)
            this.adapter = monthAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        setCalendarTitle()
                        addNextMonthToCalendar()
                    }
                }
            })
        }

        binding.ivArrowLeft.setOnClickListener {
            if (currentPosition == 0) return@setOnClickListener
            binding.vpCalendar.currentItem = currentPosition - 1
        }

        binding.ivArrowRight.setOnClickListener {
            if (currentPosition == (monthAdapter?.itemCount ?: 0) - 1) {
                addNextMonthToCalendar()
                mainHandler.postDelayed(
                    { binding.vpCalendar.currentItem = currentPosition + 1 },
                    200
                )
            } else binding.vpCalendar.currentItem = currentPosition + 1
        }

        mainHandler.postDelayed({
            setCalendarTitle()
        }, 200)
    }

    private fun setCalendarTitle() {
        val monthYear = monthAdapter?.dates?.getOrNull(currentPosition) ?: return
        binding.tvMonth.text = monthYear.month.getMonthFullText()
        binding.tvYear.text = monthYear.year.toString()
    }

    private fun getDateList(): List<Date> {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)
        return when (currentMonth) {
            10 -> listOf(
                10 to currentYear,
                11 to currentYear,
                0 to currentYear + 1
            )

            11 -> listOf(
                11 to currentYear,
                0 to currentYear + 1,
                1 to currentYear + 1
            )

            else -> listOf(
                currentMonth to currentYear,
                currentMonth + 1 to currentYear,
                currentMonth + 2 to currentYear
            )
        }.map { Date(month = it.first, year = it.second) }
    }

    private fun addNextMonthToCalendar() {
        monthAdapter?.let { adapter ->
            if (currentPosition == adapter.itemCount - 2
                || currentPosition == adapter.itemCount - 1
            ) {
                val lastDate = adapter.dates.last()
                val nextDate = if (lastDate.month == 11) {
                    Date(0, lastDate.year + 1)
                } else {
                    Date(lastDate.month + 1, lastDate.year)
                }
                adapter.dates.add(nextDate)
                adapter.notifyItemInserted(adapter.itemCount - 1)
            }
        }
    }

    override fun onClick(date: Date) {
        onDateClick?.invoke(date)
    }
}