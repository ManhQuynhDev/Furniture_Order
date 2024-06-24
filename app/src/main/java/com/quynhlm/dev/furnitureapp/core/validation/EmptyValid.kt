package com.quynhlm.dev.furnitureapp.core.validation

class EmptyValid {
    fun isValid(username: String, email: String, password: String, confirmPassword: String): Boolean {
        return !username.isEmpty() && !password.isEmpty() && !email.isEmpty() && password == confirmPassword
    }
    fun isTheSame(password: String , confirm: String) : Boolean{
        return !password.equals(confirm)
    }
    fun isEmptyLogin(username: String,password: String): Boolean {
        return !username.isEmpty() && !password.isEmpty()
    }
}