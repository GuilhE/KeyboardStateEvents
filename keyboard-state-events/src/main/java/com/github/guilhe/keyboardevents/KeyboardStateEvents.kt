package com.github.guilhe.keyboardevents

import android.content.res.Resources
import android.graphics.Rect
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.lifecycle.*
import kotlin.math.ceil

fun ComponentActivity.bindKeyboardStateEvents() {
    lifecycle.addObserver(ViewGroupHolder(findViewById(Window.ID_ANDROID_CONTENT)))
}

enum class KeyboardState { OPEN, CLOSED }

object KeyboardStateLiveData {
    private val _state = MutableLiveData<KeyboardState>()
    val state: LiveData<KeyboardState> = _state

    fun post(state: KeyboardState) {
        _state.postValue(state)
    }
}

private class ViewGroupHolder(private val root: ViewGroup) : LifecycleEventObserver {
    private val visibleThreshold = 50f
    private val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        private var previous: Boolean = isKeyboardOpen()

        override fun onGlobalLayout() {
            isKeyboardOpen().let {
                if (it != previous) {
                    KeyboardStateLiveData.post(if (it) KeyboardState.OPEN else KeyboardState.CLOSED)
                    previous = previous.not()
                }
            }
        }

        fun isKeyboardOpen(): Boolean {
            val measureRect = Rect()
            root.getWindowVisibleDisplayFrame(measureRect)
            return root.height - measureRect.bottom > ceil((visibleThreshold * Resources.getSystem().displayMetrics.density))
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_PAUSE) {
            unregisterKeyboardListener()
        } else if (event == Lifecycle.Event.ON_RESUME) {
            registerKeyboardListener()
        }
    }

    private fun registerKeyboardListener() {
        root.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    private fun unregisterKeyboardListener() {
        root.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }
}