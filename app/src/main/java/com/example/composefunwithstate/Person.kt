package com.example.composefunwithstate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Person(val name: String, var travellingFrom: String) {
    var tagged by mutableStateOf(false)
}