package com.quynhlm.dev.furnitureapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.models.User
import com.quynhlm.dev.furnitureapp.Response.ResponseObject
import com.quynhlm.dev.furnitureapp.services.RetrofitInstance
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel(){
    private val _registerState = mutableStateOf<ResponseObject?>(null)
    val registerState: State<ResponseObject?> = _registerState

    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.registerUser(user)
                if (response.isSuccessful) {
                    _registerState.value = response.body()
                    Log.e("TAG", "registerUser Body: "+ response.body())
                } else {
                    _registerState.value = ResponseObject("failed", "Registration failed","")
                    Log.e("Tag","registerUser : " + response.code())
                }
            } catch (e: Exception) {
                _registerState.value = ResponseObject("failed", "Network error: ${e.message}","")
                Log.e("TAG", "registerUser: "+ e.message)
            }
        }
    }
}