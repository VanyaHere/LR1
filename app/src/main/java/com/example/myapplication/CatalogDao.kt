package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithProductsFlow(): Flow<List<CategoryWithProducts>>

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCategoriesCount(): Int
    @Insert
    suspend fun insertCategory(category: CategoryEntity): Long

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories WHERE categoryId = :id")
    suspend fun getCategoryById(id: Long): CategoryEntity?

    @Insert
    suspend fun insertProduct(product: ProductEntity): Long

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM products WHERE productId = :id")
    suspend fun getProductById(id: Long): ProductEntity?
}
