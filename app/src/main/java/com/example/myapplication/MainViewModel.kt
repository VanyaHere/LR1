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

    val users = listOf(
        User(1, "Володимир"),
        User(2, "Микита"),
        User(3, "Олег")
    )

    val products = listOf(
        Product(1, "Яблуко", 19.99),
        Product(2, "Банан", 25.99),
        Product(3, "Апельсин", 39.49)
    )
}