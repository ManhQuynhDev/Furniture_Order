package com.quynhlm.dev.furnitureapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.Response.ResponseResult
import com.quynhlm.dev.furnitureapp.models.Shipment
import com.quynhlm.dev.furnitureapp.services.RetrofitInstance
import kotlinx.coroutines.launch

class ShipmentViewModel : ViewModel() {
    private val _shipmentState = mutableStateOf<ResponseResult<List<Shipment>>?>(null)
    val shipmentState: State<ResponseResult<List<Shipment>>?> = _shipmentState

    private val _getAnShipmentState = mutableStateOf<ResponseResult<Shipment>?>(null)
    val getAnShipmentState: State<ResponseResult<Shipment>?> = _getAnShipmentState

    fun getAllShipment() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllShipment()
                if (response.isSuccessful) {
                    _shipmentState.value = response.body()
                } else {
                    _shipmentState.value = ResponseResult("failed", "Call data not success", null)
                }
            } catch (e: Exception) {
                _shipmentState.value =
                    ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }
    fun insertShipment(shipment: Shipment) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.insertAddress(shipment)
                if (response.isSuccessful) {
                    _shipmentState.value = response.body()
                } else {
                    _shipmentState.value = ResponseResult("failed", "Insert not success", null)
                }
            } catch (e: Exception) {
                _shipmentState.value =
                    ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }
    fun deleteShipment(shipment_id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteAddress(shipment_id)
                if (response.isSuccessful) {
                    _shipmentState.value = response.body()
                } else {
                    _shipmentState.value = ResponseResult("failed", "Insert not success", null)
                }
            }catch (e : Exception){
                _shipmentState.value =
                    ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }

    fun updateShipment(shipment_id: Int,shipment: Shipment){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateAddress(shipment_id , shipment)
                if (response.isSuccessful) {
                    _shipmentState.value = response.body()
                } else {
                    _shipmentState.value = ResponseResult("failed", "Insert not success", null)
                }
            }catch (e : Exception) {
                _shipmentState.value =
                    ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }

    fun getAnShipmentById(shipment_id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAnShipmentById(shipment_id)
                if (response.isSuccessful) {
                    _getAnShipmentState.value = response.body()
                } else {
                    _getAnShipmentState.value = ResponseResult("failed", "get not success", null)
                }
            } catch (e: Exception) {
                _getAnShipmentState.value =
                    ResponseResult("failed", "Network error: ${e.message}", null)
            }
        }
    }
}
