package com.quynhlm.dev.furnitureapp.models

import com.google.gson.annotations.SerializedName


class Shipment (
    @SerializedName("shipment_id") val shipment_id : Int?,
    @SerializedName("full_name") var full_name : String,
    @SerializedName("address") var address : String,
    @SerializedName("zipcode") var zipcode : String,
    @SerializedName("province") var province : String,
    @SerializedName("district") var district : String,
    @SerializedName("ward") var ward : String,
    @SerializedName("user_id") var user_id : Int,
)