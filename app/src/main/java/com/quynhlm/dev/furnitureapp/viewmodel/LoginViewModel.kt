package com.quynhlm.dev.furnitureapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.models.User
import com.quynhlm.dev.furnitureapp.Response.ResponseObject
import com.quynhlm.dev.furnitureapp.services.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = mutableStateOf<ResponseObject?>(null)
    val loginState: State<ResponseObject?> = _loginState

    fun loginAccount(user: User) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.loginAccount(user)
                if(response.isSuccessful){
                    _loginState.value = response.body()
                }else{
                    _loginState.value = ResponseObject("failed", "Login failed",false)
                    Log.e("TAG", "loginAccount: "+response.code())
                }
            }catch (e : Exception){
                _loginState.value = ResponseObject("failed", "Network error: ${e.message}","")
            }
        }
    }
}