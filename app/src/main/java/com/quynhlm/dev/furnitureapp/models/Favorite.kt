package com.quynhlm.dev.furnitureapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite")
class Favorite (
    @PrimaryKey(autoGenerate = true) val favorite_id : Int = 0,
    @Embedded
    val product: Product
)