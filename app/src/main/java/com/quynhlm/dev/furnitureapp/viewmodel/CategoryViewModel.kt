package com.quynhlm.dev.furnitureapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.Response.ResponseResult
import com.quynhlm.dev.furnitureapp.models.Category
import com.quynhlm.dev.furnitureapp.services.RetrofitInstance
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel(){
    private val _categoryState = mutableStateOf<ResponseResult<List<Category>>?>(null)
    val categoryState: State<ResponseResult<List<Category>>?> = _categoryState

    fun getAllCategory() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllCategory()
                if(response.isSuccessful){
                    _categoryState.value = response.body()
                }else{
                    _categoryState.value = ResponseResult("failed", "Get data not success",null)
                }
            }catch (e : Exception){
                _categoryState.value = ResponseResult("failed", "Network error: ${e.message}",null)
            }
        }
    }
}