package com.example.myapplication

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.CatalogRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

class MainViewModel(application: Application) : AndroidViewModel(application) {

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

    private val repository: CatalogRepository

    var products = mutableStateListOf<ListItem>()
        private set

    var featuredProducts by mutableStateOf<List<ProductItem>>(emptyList())
        private set

    init {
        val db = AppDatabase.getInstance(application)
        repository = CatalogRepository(db.catalogDao())

        viewModelScope.launch {
            repository.listItemsFlow.collectLatest { list ->
                products.clear()
                products.addAll(
                    list + listOf(
                        NewsItem(
                            id = 1000,
                            title = "New Samsung Galaxy S25 is Out",
                            date = "11.11.2025"
                        )
                    )
                )

                featuredProducts = list.filterIsInstance<ProductItem>().take(3)
            }
        }
        preloadIfEmpty()
    }

    private fun preloadIfEmpty() {
        viewModelScope.launch {
            if (!repository.isEmpty()) return@launch

            val laptopsId = repository.addCategory("Laptops Section")
            val smartphonesId = repository.addCategory("Smartphones Section")
            val tabletsId = repository.addCategory("Tablets & Wearables")
            val accessoriesId = repository.addCategory("Accessories")

            repository.addProduct(
                categoryId = laptopsId,
                name = "Ultrabooks",
                price = "$999",
                values = listOf(
                    "Dell XPS 13",
                    "HP Spectre x360",
                    "Lenovo Yoga Slim",
                    "Asus ZenBook",
                    "Microsoft Surface Laptop"
                )
            )
            repository.addProduct(
                categoryId = laptopsId,
                name = "Gaming Laptops",
                price = "$1499",
                values = listOf(
                    "Asus ROG Strix",
                    "MSI Raider",
                    "Lenovo Legion",
                    "Acer Predator",
                    "HP Omen"
                )
            )

            repository.addProduct(
                categoryId = smartphonesId,
                name = "Flagship Phones",
                price = "$1099",
                values = listOf(
                    "iPhone 15 Pro",
                    "Samsung Galaxy S24 Ultra",
                    "Google Pixel 9 Pro",
                    "OnePlus 12",
                    "Xiaomi 14 Pro"
                )
            )
            repository.addProduct(
                categoryId = smartphonesId,
                name = "Budget Phones",
                price = "$399",
                values = listOf(
                    "Samsung Galaxy A55",
                    "Redmi Note 13",
                    "Realme 12",
                    "Motorola G Power",
                    "Nokia G22"
                )
            )

            repository.addProduct(
                categoryId = tabletsId,
                name = "Tablets",
                price = "$599",
                values = listOf(
                    "iPad Air",
                    "iPad Pro",
                    "Samsung Galaxy Tab S9",
                    "Xiaomi Pad 6",
                    "Lenovo Tab P12"
                )
            )
            repository.addProduct(
                categoryId = tabletsId,
                name = "Smartwatches",
                price = "$299",
                values = listOf(
                    "Apple Watch Series 10",
                    "Samsung Galaxy Watch7",
                    "Garmin Venu 3",
                    "Huawei Watch GT",
                    "Xiaomi Watch 2"
                )
            )

            repository.addProduct(
                categoryId = accessoriesId,
                name = "Laptop Accessories",
                price = "$79",
                values = listOf(
                    "Wireless Mouse",
                    "Mechanical Keyboard",
                    "USB-C Docking Station",
                    "External SSD",
                    "Laptop Stand"
                )
            )
            repository.addProduct(
                categoryId = accessoriesId,
                name = "Phone Accessories",
                price = "$29",
                values = listOf(
                    "MagSafe Charger",
                    "Power Bank",
                    "Bluetooth Earbuds",
                    "Protective Case",
                    "Screen Protector"
                )
            )
        }
    }

    fun addProductSimple(categoryId: Long, name: String, price: String, values: List<String>) {
        viewModelScope.launch {
            repository.addProduct(categoryId, name, price, values)
        }
    }

    fun updateProductSimple(id: Int, name: String, price: String, values: List<String>) {
        viewModelScope.launch {
            repository.updateProduct(id.toLong(), name, price, values)
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            repository.deleteProduct(id.toLong())
        }
    }

    fun addCategory(name: String) {
        viewModelScope.launch {
            repository.addCategory(name)
        }
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch {
            repository.deleteCategory(id.toLong())
        }
    }
}
