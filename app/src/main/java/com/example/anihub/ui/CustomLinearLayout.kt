package com.example.anihub.ui

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayout : LinearLayoutManager {

    private var isScrollingEnabled = false

    constructor(context: Context?) : super(context)

    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    fun setScrollingEnabled(enabled: Boolean) {
        isScrollingEnabled = enabled
    }

    override fun canScrollVertically(): Boolean {
        return isScrollingEnabled
    }
}