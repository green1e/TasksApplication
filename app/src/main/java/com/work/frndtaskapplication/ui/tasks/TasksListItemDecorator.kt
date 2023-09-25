package com.work.frndtaskapplication.ui.tasks

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TasksListItemDecorator(private val marginTop: Int, private val marginBottom: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position == 0) outRect.top = marginTop
        else if (position == state.itemCount - 1) outRect.bottom = marginBottom
    }
}