package com.quynhlm.dev.furnitureapp.Database.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.quynhlm.dev.furnitureapp.models.Cart
import com.quynhlm.dev.furnitureapp.models.Favorite

@Database(entities = [Cart::class , Favorite::class], version = 1)
abstract class Db_Helper : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INTANCE: Db_Helper? = null

        fun getInstance(context: Context): Db_Helper {
            synchronized(this) {
                var intance = INTANCE
                if (intance == null) {
                    intance = Room.databaseBuilder(
                        context.applicationContext,
                        Db_Helper::class.java,
                        "Db_Helper"
                    ).build()
                }
                return intance!!
            }
        }
    }
}