package com.quynhlm.dev.furnitureapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.Response.CategoryResponse
import com.quynhlm.dev.furnitureapp.services.RetrofitInstance
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel(){
    private val _categoryState = mutableStateOf<CategoryResponse?>(null)
    val categoryState: State<CategoryResponse?> = _categoryState

    fun getAllCategory() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllCategory()
                if(response.isSuccessful){
                    _categoryState.value = response.body()
                }else{
                    _categoryState.value = CategoryResponse("failed", "Login failed",null)
                }
            }catch (e : Exception){
                _categoryState.value = CategoryResponse("failed", "Network error: ${e.message}",null)
            }
        }
    }
}