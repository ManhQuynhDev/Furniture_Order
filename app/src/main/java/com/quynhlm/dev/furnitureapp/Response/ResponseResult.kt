package com.quynhlm.dev.furnitureapp.Response

class ResponseResult <T> (
    val status: String,
    val message: String,
    val data: T?
)