package com.quynhlm.dev.furnitureapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.models.User
import com.quynhlm.dev.furnitureapp.Response.ResponseResult
import com.quynhlm.dev.furnitureapp.services.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    fun loginAccount(user: User) : Boolean{
        var isCheckSuccess = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.loginAccount(user)
                if(response.isSuccessful){
                    isCheckSuccess = true
                }else{
                    isCheckSuccess = false
                }
            }catch (e : Exception){
                isCheckSuccess = false
            }
        }
        return isCheckSuccess
    }
}