package com.example.composefunwithstate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var openAddPersonDialog by mutableStateOf(false)

    val peopleState = mutableStateListOf(
        Person("James", "London"),
        Person("Mantas", "Vilnius"),
        Person("Rick", "Stockholm"),
        Person("Jeanette", "Aarhus"),
        Person("Martin", "Paris"),
        Person("Diego", "Rio"),
        Person("Vytautas", "Kaunas")
    )



    fun addPerson(name: String, city: String) {
        peopleState.add(Person(name = name, travellingFrom = city))
    }

    fun removePerson(position: Int) {
        peopleState.removeAt(position)
    }

    fun changeCity(position: Int, cityName: String) {
        peopleState[position].travellingFrom = cityName
    }

    fun tagRow(position: Int) {
        peopleState[position].apply {
            tagged = !tagged
        }
    }

}
