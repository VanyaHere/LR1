package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainViewModel : ViewModel() {

    var homeText by mutableStateOf("Головний екран")
        private set

    fun updateHomeText() {
        homeText = "Текст змінено через ViewModel!"
    }

    var aboutText by mutableStateOf("Екран About")
        private set

    fun updateAboutText() {
        aboutText = "Текст змінено ViewModel!"
    }

    var settingsText by mutableStateOf("Налаштування")
        private set

    fun updateSettingsText() {
        settingsText = "My ViewModel!"
    }
}