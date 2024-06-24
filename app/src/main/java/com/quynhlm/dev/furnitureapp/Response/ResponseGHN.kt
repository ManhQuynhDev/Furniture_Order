package com.quynhlm.dev.furnitureapp.Response

import com.quynhlm.dev.furnitureapp.models.Product

class ResponseGHN <T> (
    val code : Int,
    val message: String,
    val data: T?
)