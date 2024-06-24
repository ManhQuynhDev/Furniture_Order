package com.quynhlm.dev.furnitureapp.Response

import com.quynhlm.dev.furnitureapp.models.Product

class ProductResponse(
    val status: String,
    val message: String,
    val data: List<Product>?
)