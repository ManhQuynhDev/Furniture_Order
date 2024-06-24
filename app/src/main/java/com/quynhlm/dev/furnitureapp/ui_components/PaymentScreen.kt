package com.quynhlm.dev.furnitureapp.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.quynhlm.dev.furnitureapp.Database.DB.Db_Helper
import com.quynhlm.dev.furnitureapp.Database.Repository.CartRepository
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.models.Order
import com.quynhlm.dev.furnitureapp.viewmodel.CartViewModel
import com.quynhlm.dev.furnitureapp.viewmodel.OrderViewModel
import com.quynhlm.dev.furnitureapp.viewmodel.ShipmentViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun PaymentScreen(innerPadding: PaddingValues, navHostController: NavController , shipment_id : Int?) {
    var shipmentViewModel: ShipmentViewModel = viewModel()
    var getAnShipmentState  = shipmentViewModel.getAnShipmentState
    var orderViewModel : OrderViewModel = viewModel()
    val context = LocalContext.current
    val db = Db_Helper.getInstance(context)
    val repository = CartRepository(db)
    val cartViewModel = CartViewModel(repository)
    val cartList by cartViewModel.getAllCarts().observeAsState(initial = emptyList())
    val totalAmount = remember(cartList) {
        calculateTotalPrice(cartList)
    }


    LaunchedEffect(Unit) {
        shipmentViewModel.getAnShipmentById(shipment_id!!)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(start = 14.dp, innerPadding.calculateTopPadding(), end = 15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Shipping Address",
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600),
                        color = Color("#909090".toColorInt())
                    )
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navHostController.navigate("selectShipment") }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(Color("#ffffff".toColorInt()))
                        .shadow(elevation = 0.dp, RoundedCornerShape(8.dp), clip = true),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    getAnShipmentState?.let { state ->
                        if (state.value?.status == "ok" && state.value?.data != null) {
                            val shipmentData = state.value!!.data!!
                            Text(
                                text = shipmentData.full_name,
                                modifier = Modifier.padding(start = 15.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold))
                            )
                            Divider(
                                color = Color.Gray,
                                thickness = 1.5.dp
                            )
                            Column(modifier = Modifier.padding(start = 15.dp)) {
                                Text(
                                    text = "${shipmentData.address.replace("+", " ")}, ${shipmentData.zipcode}",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                                    fontWeight = FontWeight(500)
                                )
                                Text(
                                    text = "${shipmentData.ward.replace("+", " ")}, ${shipmentData.district.replace("+", " ")}, ${shipmentData.province.replace("+", " ")}",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold))
                                )
                            }
                        } else {
                            Text(
                                text = "Please select your address !",
                                color = Color.Red,
                                modifier = Modifier.padding(start = 15.dp)
                            )
                        }
                    } ?: run {
                        Text(
                            text = "Loading...",
                            modifier = Modifier.padding(start = 15.dp),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Payment",
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600),
                        color = Color("#909090".toColorInt())
                    )
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navHostController.navigate("paymentMethod") }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .background(Color("#ffffff".toColorInt()))
                        .shadow(0.dp, RoundedCornerShape(8.dp), clip = true),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.method),
                            contentDescription = null,
                            modifier = Modifier
                                .height(64.dp)
                                .width(64.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        text = "**** **** **** 3947",
                        modifier = Modifier,
                        fontSize = 17.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily(
                            Font(R.font.nunitosans_7pt_condensed_bold)
                        )
                    )
                    Row {

                    }
                    Row {

                    }
                    Row {

                    }
                }
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Delivery method",
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600),
                        color = Color("#909090".toColorInt())
                    )
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .background(Color("#ffffff".toColorInt()))
                        .shadow(0.dp, RoundedCornerShape(8.dp), clip = true),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.dhl),
                            contentDescription = null,
                            modifier = Modifier
                                .height(20.dp)
                                .width(88.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        text = "Fast (2-3days)",
                        modifier = Modifier,
                        fontSize = 17.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily(
                            Font(R.font.nunitosans_7pt_condensed_bold)
                        )
                    )
                    Row {

                    }
                    Row {

                    }
                    Row {

                    }
                }
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Price",
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600),
                        color = Color("#909090".toColorInt())
                    )
                    Row() {

                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(135.dp)
                        .background(Color("#ffffff".toColorInt()))
                        .shadow(0.dp, RoundedCornerShape(8.dp), clip = true),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Order:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                            color = Color.Gray
                        )
                        Text(
                            text = "\$ " + totalAmount,
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                            color = Color.Black
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Delivery:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                            color = Color.Gray
                        )
                        Text(
                            text = "\$ 25.000",
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                            color = Color.Black
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                            color = Color.Gray
                        )
                        Text(
                            text = "\$ " + (totalAmount + 25000),
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                            color = Color.Black
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF242424))
                    .clickable(onClick = {
                        val date = Date()
                        val localDate = date
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                        val formattedDate = localDate.format(formatter)
                        for (cart in cartList) {
                            val orderItem = Order(
                                null,
                                1,
                                cart.product.product_id,
                                shipment_id!!,
                                cart.quantity,
                                formattedDate,
                                0
                            )
                            try {
                                orderViewModel.insertOrder(orderItem)
                                cartViewModel.deleteCart(cart)
                                showMessage(context , "Order successfully")
                                navHostController.navigate("success")
                            }catch (e : Exception){
                                showMessage(context , "Order not successfully")
                            }
                        }
                    })
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "SUBMIT ORDER",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                        fontWeight = FontWeight(600),
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarPayment(navHostController: NavController , shipment_id : Int?) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "Check Out",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = FontFamily(
                            Font(R.font.merriweather_regular)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigateUp()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowback),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { /* do something */ }) {
                    }
                },
            )
        },
    ) { innerPadding ->
        PaymentScreen(innerPadding , navHostController , shipment_id)
    }
}