package com.example.composeplayground

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class DemoViewModel : ViewModel(){
    var bgColor by mutableStateOf(Color.Gray)
        private set

    fun changeBgColor(value: Boolean) {
        bgColor = if (value) {
            Color.DarkGray
        } else {
            Color.Gray
        }
//        bgColor = Color.DarkGray
    }
}