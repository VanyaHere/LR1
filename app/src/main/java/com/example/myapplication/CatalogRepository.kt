package com.example.myapplication.data

import com.example.myapplication.HeaderItem
import com.example.myapplication.ListItem
import com.example.myapplication.ProductItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CatalogRepository(
    private val dao: CatalogDao
) {

    val listItemsFlow: Flow<List<ListItem>> =
        dao.getCategoriesWithProductsFlow().map { categoriesWithProducts ->
            val result = mutableListOf<ListItem>()

            categoriesWithProducts.forEach { cwp ->

                if (cwp.products.isNotEmpty()) {

                    result.add(
                        HeaderItem(
                            id = cwp.category.categoryId.toInt(),
                            title = cwp.category.name
                        )
                    )

                    cwp.products.forEach { p ->
                        result.add(
                            ProductItem(
                                id = p.productId.toInt(),
                                title = p.name,
                                price = p.minPrice,
                                values = if (p.details.isBlank())
                                    emptyList()
                                else
                                    p.details.split("|")
                            )
                        )
                    }
                }
            }

            result
        }

    suspend fun isEmpty(): Boolean {
        return dao.getCategoriesCount() == 0
    }

    suspend fun addCategory(name: String): Long {
        return dao.insertCategory(CategoryEntity(name = name))
    }

    suspend fun updateCategory(id: Long, name: String) {
        val old = dao.getCategoryById(id) ?: return
        dao.updateCategory(old.copy(name = name))
    }

    suspend fun deleteCategory(id: Long) {
        val old = dao.getCategoryById(id) ?: return
        dao.deleteCategory(old)
    }

    suspend fun addProduct(
        categoryId: Long,
        name: String,
        price: String,
        values: List<String>
    ): Long {
        val details = values.joinToString("|")
        return dao.insertProduct(
            ProductEntity(
                name = name,
                minPrice = price,
                details = details,
                categoryOwnerId = categoryId
            )
        )
    }

    suspend fun updateProduct(
        id: Long,
        name: String,
        price: String,
        values: List<String>
    ) {
        val old = dao.getProductById(id) ?: return
        dao.updateProduct(
            old.copy(
                name = name,
                minPrice = price,
                details = values.joinToString("|")
            )
        )
    }

    suspend fun deleteProduct(id: Long) {
        val old = dao.getProductById(id) ?: return
        dao.deleteProduct(old)
    }
}
