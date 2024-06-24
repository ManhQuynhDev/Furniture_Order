package com.quynhlm.dev.furnitureapp.Database.Repository

import androidx.lifecycle.LiveData
import com.quynhlm.dev.furnitureapp.Database.DB.Db_Helper
import com.quynhlm.dev.furnitureapp.models.Cart
import com.quynhlm.dev.furnitureapp.models.Favorite

class FavoriteRepository (val dbHelper: Db_Helper) {
    suspend fun addFavorite(favorite: Favorite) {
        dbHelper.favoriteDao().insert(favorite)
    }
    fun getAllFavorites() : LiveData<List<Favorite>> = dbHelper.favoriteDao().getAllData()

    suspend fun deleteFavorite(favorite: Favorite){
        dbHelper.favoriteDao().deleteFavorite(favorite)
    }
}