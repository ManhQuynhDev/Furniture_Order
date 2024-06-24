package com.quynhlm.dev.furnitureapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.Database.Repository.CartRepository
import com.quynhlm.dev.furnitureapp.Database.Repository.FavoriteRepository
import com.quynhlm.dev.furnitureapp.models.Cart
import com.quynhlm.dev.furnitureapp.models.Favorite
import kotlinx.coroutines.launch

class FavoriteViewModel(val repository: FavoriteRepository) : ViewModel(){
    fun addToFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.addFavorite(favorite)
        }
    }
    fun getAllFavorite() : LiveData<List<Favorite>> = repository.getAllFavorites()

    fun deleteFavorite(favorite: Favorite){
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    }
}