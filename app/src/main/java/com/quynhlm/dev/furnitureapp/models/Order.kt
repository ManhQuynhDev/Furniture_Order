package com.quynhlm.dev.furnitureapp.models

import com.google.gson.annotations.SerializedName

class Order (
    @SerializedName("order_id") val order_id : Int?,
    @SerializedName("user_id") var user_id : Int,
    @SerializedName("product_id") var product_id : Int,
    @SerializedName("shipment_id") var shipment_id : Int,
    @SerializedName("quantity") var quantity : Int,
    @SerializedName("date") var date : String,
    @SerializedName("state") var state : Int,
){
    override fun toString(): String {
        return "Order(user_id=$user_id, product_id=$product_id, shipment_id=$shipment_id, quantity=$quantity, date='$date', state=$state)"
    }
}