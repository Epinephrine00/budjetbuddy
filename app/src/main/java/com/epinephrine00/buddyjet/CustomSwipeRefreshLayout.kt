package com.epinephrine00.buddyjet

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class CustomSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    private var initialY = 0f
    private var directionUp = false
    private var directionDown = false

    var onRefreshUpListener: (() -> Unit)? = null
    var onRefreshDownListener: (() -> Unit)? = null

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            initialY = ev.y
            directionUp = false
            directionDown = false
        } else if (ev?.action == MotionEvent.ACTION_MOVE) {
            val y = ev.y
            val dy = y - initialY
            if (dy > 0) {
                directionDown = true
            } else if (dy < 0) {
                directionUp = true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (directionDown && !isRefreshing && ev?.action == MotionEvent.ACTION_UP) {
            onRefreshDownListener?.invoke()
            isRefreshing = true
        } else if (directionUp && !isRefreshing && ev?.action == MotionEvent.ACTION_UP) {
            onRefreshUpListener?.invoke()
            isRefreshing = true
        }
        return super.onTouchEvent(ev)
    }

    fun setRefreshing(isRefreshing: Boolean, direction: String) {
        if (direction == "UP" && directionUp) {
            this.isRefreshing = isRefreshing
        } else if (direction == "DOWN" && directionDown) {
            this.isRefreshing = isRefreshing
        }
    }
}
