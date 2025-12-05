package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryOwnerId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryOwnerId")]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0,
    val name: String,
    val minPrice: String,
    val details: String,
    val categoryOwnerId: Long
)
