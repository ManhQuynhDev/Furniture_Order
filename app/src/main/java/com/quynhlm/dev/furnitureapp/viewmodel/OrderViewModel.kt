package com.quynhlm.dev.furnitureapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.Response.ResponseResult
import com.quynhlm.dev.furnitureapp.models.Order
import com.quynhlm.dev.furnitureapp.models.Shipment
import com.quynhlm.dev.furnitureapp.services.RetrofitInstance
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel(){
    private val _orderState = mutableStateOf<ResponseResult<List<Order>>?>(null)
    val orderState: State<ResponseResult<List<Order>>?> = _orderState

    fun getAllOrder() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllOrder()
                if (response.isSuccessful) {
                    _orderState.value = response.body()
                } else {
                    _orderState.value = ResponseResult("failed", "Call data not success", null)
                }
            } catch (e: Exception) {
                _orderState.value =
                    ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }

    fun insertOrder(order: Order) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.insertOrder(order)
                if (response.isSuccessful) {
                    _orderState.value = response.body()
                } else {
                    _orderState.value = ResponseResult("failed", "Insert not success", null)
                }
            } catch (e: Exception) {
                _orderState.value =
                    ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }
    fun updateOrder(order_id : Int , order: Order){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateOrder(order_id,order)
                if (response.isSuccessful) {
                    _orderState.value = response.body()
                    getAllOrder()
                } else {
                    _orderState.value = ResponseResult("failed", "Update not success", null)
                }
            } catch (e: Exception) {
                _orderState.value =
                    ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }
}