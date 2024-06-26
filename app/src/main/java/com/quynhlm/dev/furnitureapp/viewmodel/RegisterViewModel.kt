package com.quynhlm.dev.furnitureapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.models.User
import com.quynhlm.dev.furnitureapp.Response.ResponseResult
import com.quynhlm.dev.furnitureapp.services.RetrofitInstance
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel(){
    //Status data
//    private val _registerState = mutableStateOf<ResponseResult<Any>?>(null)
//    val registerState: State<ResponseResult<Any>?> = _registerState

    //Function Register User
    fun registerUser(user: User) : Boolean {
        var isCheckSuccess = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.registerUser(user)
                if (response.isSuccessful) {
                    isCheckSuccess = true
                    Log.e("TAG", "registerUser Body: "+ response.body())
                } else {
                    isCheckSuccess = false
//                    _registerState.value = ResponseResult("failed", "Registration failed","")
                    Log.e("Tag","registerUser : " + response.code())
                }
            } catch (e: Exception) {
                isCheckSuccess = false
//                _registerState.value = ResponseResult("failed", "Network error: ${e.message}","")
                Log.e("TAG", "registerUser: "+ e.message)
            }
        }
        return isCheckSuccess
    }
}