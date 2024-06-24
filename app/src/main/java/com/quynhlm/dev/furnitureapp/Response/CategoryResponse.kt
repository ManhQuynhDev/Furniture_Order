package com.quynhlm.dev.furnitureapp.Response

import com.quynhlm.dev.furnitureapp.models.Category

class CategoryResponse(
    val status: String,
    val message: String,
    val data: List<Category>?
)