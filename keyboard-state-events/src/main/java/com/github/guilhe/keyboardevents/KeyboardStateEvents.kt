package com.github.guilhe.keyboardevents

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.lifecycle.*
import kotlin.math.ceil

fun ComponentActivity.bindKeyboardStateEvents() {
    lifecycle.addObserver(ViewGroupHolder(findViewById(Window.ID_ANDROID_CONTENT)))
}

fun ComponentActivity.toggleKeyboard() {
    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInputFromWindow(findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT).applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
}

fun ComponentActivity.dismissKeyboard() {
    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT).windowToken, 0)
}

fun ComponentActivity.isKeyboardOpen(visibleThreshold: Float = 100f): Boolean {
    val root = findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
    val measureRect = Rect()
    root.getWindowVisibleDisplayFrame(measureRect)
    return root.rootView.height - measureRect.bottom > ceil((visibleThreshold * Resources.getSystem().displayMetrics.density))
}

fun ViewGroup.isKeyboardOpen(visibleThreshold: Float = 100f): Boolean {
    val measureRect = Rect()
    getWindowVisibleDisplayFrame(measureRect)
    return rootView.height - measureRect.bottom > ceil((visibleThreshold * Resources.getSystem().displayMetrics.density))
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
    private val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        private var previous: Boolean = root.isKeyboardOpen()

        override fun onGlobalLayout() {
            root.isKeyboardOpen().let {
                if (it != previous) {
                    KeyboardStateLiveData.post(if (it) KeyboardState.OPEN else KeyboardState.CLOSED)
                    previous = previous.not()
                }
            }
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
        root.viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }
}
