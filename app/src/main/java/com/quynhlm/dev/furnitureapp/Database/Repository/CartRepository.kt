package com.quynhlm.dev.furnitureapp.Database.Repository

import androidx.lifecycle.LiveData
import com.quynhlm.dev.furnitureapp.Database.DB.Db_Helper
import com.quynhlm.dev.furnitureapp.models.Cart

class CartRepository(val dbHelper: Db_Helper) {
    suspend fun addToCart(cart: Cart) {
        dbHelper.cartDao().insert(cart)
    }
    fun getAllCarts() : LiveData<List<Cart>> = dbHelper.cartDao().getAllData()

    suspend fun deleteCart(cart: Cart){
        dbHelper.cartDao().deleteCart(cart)
    }
    suspend fun updateCart(cart: Cart){
        dbHelper.cartDao().updateCart(cart)
    }
}