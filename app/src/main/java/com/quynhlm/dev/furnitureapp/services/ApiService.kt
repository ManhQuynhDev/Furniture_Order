package com.quynhlm.dev.furnitureapp.services

import com.quynhlm.dev.furnitureapp.Response.CategoryResponse
import com.quynhlm.dev.furnitureapp.Response.ProductResponse
import com.quynhlm.dev.furnitureapp.Response.ResponseObject
import com.quynhlm.dev.furnitureapp.Response.ResponseResult
import com.quynhlm.dev.furnitureapp.models.Order
import com.quynhlm.dev.furnitureapp.models.Product
import com.quynhlm.dev.furnitureapp.models.Shipment
import com.quynhlm.dev.furnitureapp.models.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("users")
    suspend fun registerUser(@Body user: User): Response<ResponseObject>

    @POST("users/login")
    suspend fun loginAccount(@Body user: User) : Response<ResponseObject>

    @GET("category")
    suspend fun getAllCategory() : Response<CategoryResponse>

    @GET("product")
    suspend fun getAllProduct() : Response<ProductResponse>

    @GET("product/{product_id}")
    suspend fun getAnProductById(@Path("product_id") product_id : Int) : Response<ResponseResult<Product>>

    @GET("shipment")
    suspend fun getAllShipment() : Response<ResponseResult<List<Shipment>>>

    @GET("shipment/{shipment_id}")
    suspend fun getAnShipmentById(@Path("shipment_id") shipment_id : Int) : Response<ResponseResult<Shipment>>

    @POST("shipment")
    suspend fun insertAddress(@Body shipment: Shipment) : Response<ResponseResult<List<Shipment>>>

    @PUT("shipment/{shipment_id}")
    suspend fun updateAddress(@Path("shipment_id") shipment_id : Int , @Body shipment: Shipment) : Response<ResponseResult<List<Shipment>>>

    @DELETE("shipment/{shipment_id}")
    suspend fun deleteAddress(@Path("shipment_id") shipment_id : Int ) : Response<ResponseResult<List<Shipment>>>

    @GET("order")
    suspend fun getAllOrder() : Response<ResponseResult<List<Order>>>

    @POST("order")
    suspend fun insertOrder(@Body order: Order) : Response<ResponseResult<List<Order>>>

    @PUT("order/{order_id}")
    suspend fun updateOrder(@Path("order_id") order_id : Int , @Body order: Order) : Response<ResponseResult<List<Order>>>
}
    object RetrofitInstance {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }