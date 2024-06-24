package com.quynhlm.dev.furnitureapp.models

import Converters
import com.quynhlm.dev.furnitureapp.models.Product
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.TypeConverters

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true) val cart_id : Int? = 0,
    @Embedded val product: Product,
    val quantity: Int
)