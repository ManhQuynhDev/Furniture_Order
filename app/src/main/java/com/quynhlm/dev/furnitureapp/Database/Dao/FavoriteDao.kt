package com.quynhlm.dev.furnitureapp.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.quynhlm.dev.furnitureapp.models.Favorite

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    fun getAllData() : LiveData<List<Favorite>>

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}