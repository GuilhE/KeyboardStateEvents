package com.github.guilhe.keyboardevents.sample

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.guilhe.keyboardevents.KeyboardStateLiveData
import com.github.guilhe.keyboardevents.KeyboardState
import com.github.guilhe.keyboardevents.KeyboardState.OPEN
import com.github.guilhe.keyboardevents.bindKeyboardEvents
import com.github.guilhe.keyboardevents.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var currentState = KeyboardState.CLOSED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tapAppCompatButton.setOnClickListener {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            when (currentState) {
                OPEN -> imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                else -> imm.toggleSoftInputFromWindow(binding.root.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
            }
        }

        KeyboardStateLiveData.state.observe(this, Observer {
            binding.stateTextView.text = String.format(getString(R.string.keyboard), it.name)
        })
        bindKeyboardEvents()
    }
}