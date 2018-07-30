package com.sktelecom.showme.base.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NonSwipeableViewPager : ViewPager {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onInterceptTouchEvent(event)
    }

    companion object {

        internal var isPagingEnabled = false

        fun setPagingEnabled(b: Boolean) {
            isPagingEnabled = b
        }
    }
}
