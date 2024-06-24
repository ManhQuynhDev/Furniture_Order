package com.quynhlm.dev.furnitureapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_account")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") val userId: Int = 0,

    @ColumnInfo(name = "username") var username: String = "",

    @ColumnInfo(name = "password") var password: String = "",
)