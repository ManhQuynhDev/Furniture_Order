package com.quynhlm.dev.furnitureapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id") val category_id: Int = 0,

    @ColumnInfo(name = "name") var name: String = "",

    @ColumnInfo(name = "image") var image: String = ""
)