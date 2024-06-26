package com.quynhlm.dev.furnitureapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.Response.ResponseResult
import com.quynhlm.dev.furnitureapp.models.Product
import com.quynhlm.dev.furnitureapp.services.RetrofitInstance
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _productState = mutableStateOf<ResponseResult<List<Product>>?>(null)
    val productState: State<ResponseResult<List<Product>>?> = _productState

    private val _getAnProductState = mutableStateOf<ResponseResult<Product>?>(null)
    val getAnProductState: State<ResponseResult<Product>?> = _getAnProductState

    fun getAllProduct() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllProduct()
                if (response.isSuccessful) {
                    _productState.value = response.body()
                } else {
                    _productState.value = ResponseResult("failed", "Call data not success", null)
                }
            } catch (e: Exception) {
                _productState.value = ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }

    fun getAnProductByID(product_id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAnProductById(product_id)
                if (response.isSuccessful) {
                    _getAnProductState.value = response.body()
                } else {
                    _getAnProductState.value =
                        ResponseResult("failed", "Call data not success", null)
                }
            } catch (e: Exception) {
                _getAnProductState.value =
                    ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }
}