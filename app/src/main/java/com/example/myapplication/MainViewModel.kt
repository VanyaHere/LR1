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
    fun refreshData() {
        viewModelScope.launch {
            repository.refreshFromApi()
        }
    }

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

        // Підписка на дані з Room
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
            repository.refreshFromApi()
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
    fun addProductFromButton() {
        viewModelScope.launch {
            repository.addProductToAnyCategory(
                name = "New Product",
                price = "$99",
                values = listOf("Value A", "Value B")
            )
        }
    }

}
