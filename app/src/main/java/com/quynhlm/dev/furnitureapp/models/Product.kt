package com.quynhlm.dev.furnitureapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
class Product (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id") val product_id: Int = 0,

    @ColumnInfo(name = "name") var name: String = "",

    @ColumnInfo(name = "image") var image: String = "",

    @ColumnInfo(name = "price") var price: Float,

    @ColumnInfo(name = "category_id") var category_id: Int,

    @ColumnInfo(name = "description") var description: String,

    @ColumnInfo(name = "rating") var rating: Float
)