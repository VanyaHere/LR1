package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

interface ListItem

data class ProductItem(
    val id: Int,
    val title: String,
    val price: String,
    val values: List<String>
) : ListItem

data class HeaderItem(
    val id: Int,
    val title: String
) : ListItem

data class NewsItem(
    val id: Int,
    val title: String,
    val date: String
) : ListItem


class MainViewModel : ViewModel() {

    // Те, що вже було
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

    var products = mutableStateListOf<ListItem>()
        private set

    init {
        loadTestData()
    }

    var featuredProducts = listOf(
        ProductItem(
            id = 101,
            title = "iPhone 15 Pro",
            price = "$1099",
            values = listOf("256GB", "Titanium", "A17 Pro")
        ),
        ProductItem(
            id = 102,
            title = "Samsung S24 Ultra",
            price = "$1199",
            values = listOf("512GB", "200MP", "Snapdragon")
        ),
        ProductItem(
            id = 103,
            title = "MacBook Air M3",
            price = "$1299",
            values = listOf("16GB RAM", "512GB SSD", "M3")
        )
    )

    private fun loadTestData() {
        products.addAll(
            listOf(
                HeaderItem(1, "Laptops Section"),
                ProductItem(
                    id = 2,
                    title = "Ultrabooks",
                    price = "$999",
                    values = listOf(
                        "Dell XPS 13",
                        "HP Spectre x360",
                        "Lenovo Yoga Slim",
                        "Asus ZenBook",
                        "Microsoft Surface Laptop"
                    )
                ),
                ProductItem(
                    id = 3,
                    title = "Gaming Laptops",
                    price = "$1499",
                    values = listOf(
                        "Asus ROG Strix",
                        "MSI Raider",
                        "Lenovo Legion",
                        "Acer Predator",
                        "HP Omen"
                    )
                ),
                NewsItem(
                    id = 7,
                    title = "New Samsung Galaxy S25 is Out",
                    date = "11.11.2025"
                ),
                HeaderItem(4, "Smartphones Section"),
                ProductItem(
                    id = 5,
                    title = "Flagship Phones",
                    price = "$1099",
                    values = listOf(
                        "iPhone 15 Pro",
                        "Samsung Galaxy S24 Ultra",
                        "Google Pixel 9 Pro",
                        "OnePlus 12",
                        "Xiaomi 14 Pro"
                    )
                ),
                ProductItem(
                    id = 6,
                    title = "Budget Phones",
                    price = "$399",
                    values = listOf(
                        "Samsung Galaxy A55",
                        "Redmi Note 13",
                        "Realme 12",
                        "Motorola G Power",
                        "Nokia G22"
                    )
                ),

                HeaderItem(8, "Tablets & Wearables"),
                ProductItem(
                    id = 9,
                    title = "Tablets",
                    price = "$599",
                    values = listOf(
                        "iPad Air",
                        "iPad Pro",
                        "Samsung Galaxy Tab S9",
                        "Xiaomi Pad 6",
                        "Lenovo Tab P12"
                    )
                ),
                ProductItem(
                    id = 10,
                    title = "Smartwatches",
                    price = "$299",
                    values = listOf(
                        "Apple Watch Series 10",
                        "Samsung Galaxy Watch7",
                        "Garmin Venu 3",
                        "Huawei Watch GT",
                        "Xiaomi Watch 2"
                    )
                ),

                HeaderItem(11, "Accessories"),
                ProductItem(
                    id = 12,
                    title = "Laptop Accessories",
                    price = "$79",
                    values = listOf(
                        "Wireless Mouse",
                        "Mechanical Keyboard",
                        "USB-C Docking Station",
                        "External SSD",
                        "Laptop Stand"
                    )
                ),
                ProductItem(
                    id = 13,
                    title = "Phone Accessories",
                    price = "$29",
                    values = listOf(
                        "MagSafe Charger",
                        "Power Bank",
                        "Bluetooth Earbuds",
                        "Protective Case",
                        "Screen Protector"
                    )
                )
            )
        )
    }
}