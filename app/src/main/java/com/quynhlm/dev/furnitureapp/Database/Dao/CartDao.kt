package com.quynhlm.dev.furnitureapp.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.quynhlm.dev.furnitureapp.models.Cart

@Dao
interface CartDao {
    @Insert
    suspend fun insert(cart: Cart)

    @Query("SELECT * FROM cart")
    fun getAllData() : LiveData<List<Cart>>

    @Delete
    suspend fun deleteCart(cart: Cart)

    @Update
    suspend fun updateCart(cart: Cart)
}