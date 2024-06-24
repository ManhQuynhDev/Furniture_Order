package com.quynhlm.dev.furnitureapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.Database.Repository.CartRepository
import com.quynhlm.dev.furnitureapp.models.Cart
import kotlinx.coroutines.launch

class CartViewModel(val repository: CartRepository) : ViewModel(){
    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            repository.addToCart(cart)
        }
    }
    fun getAllCarts() : LiveData<List<Cart>> = repository.getAllCarts()

    fun deleteCart(cart: Cart){
        viewModelScope.launch {
            repository.deleteCart(cart)
        }
    }
    fun updateCart (cart: Cart){
        viewModelScope.launch {
            repository.updateCart(cart)
        }
    }
}