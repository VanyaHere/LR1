package com.example.myapplication.data

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithProducts(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryOwnerId"
    )
    val products: List<ProductEntity>
)
