package com.quynhlm.dev.furnitureapp.services

import com.quynhlm.dev.furnitureapp.Response.ResponseGHN
import com.quynhlm.dev.furnitureapp.models.District
import com.quynhlm.dev.furnitureapp.models.DistrictRequest
import com.quynhlm.dev.furnitureapp.models.Province
import com.quynhlm.dev.furnitureapp.models.Ward
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GHNService {

    @GET("/shiip/public-api/master-data/province")
    fun getListProvince(): Call<ResponseGHN<List<Province?>?>?>?

    @POST("/shiip/public-api/master-data/district")
    fun getListDistrict(@Body districtRequest: DistrictRequest?): Call<ResponseGHN<List<District?>?>?>?

    @GET("/shiip/public-api/master-data/ward")
    fun getListWard(@Query("district_id") district_id: Int): Call<ResponseGHN<List<Ward?>?>?>?

}

object GHNInstance {
    private const val SHOP_ID = "191530"
    private const val TOKEN_GHN = "d01c7b44-ec5d-11ee-8bfa-8a2dda8ec551"

    val apiService: GHNService by lazy {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("ShopId", SHOP_ID)
                .addHeader("Token", TOKEN_GHN)
                .build()
            chain.proceed(request)
        })
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev-online-gateway.ghn.vn/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        retrofit.create(GHNService::class.java)
    }
}
