package com.github.guilhe.keyboardevents.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.guilhe.keyboardevents.*
import com.github.guilhe.keyboardevents.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tapAppCompatButton.setOnClickListener {
            //We could just call toggleKeyboard()
            if (isKeyboardOpen()) dismissKeyboard() else toggleKeyboard()
        }

        KeyboardStateLiveData.state.observe(this, Observer {
            binding.stateTextView.text = String.format(getString(R.string.keyboard), it.name)
        })
        bindKeyboardStateEvents()
    }
}