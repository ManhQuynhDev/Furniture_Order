package com.quynhlm.dev.furnitureapp.ui_components

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.models.Order
import com.quynhlm.dev.furnitureapp.models.Product
import com.quynhlm.dev.furnitureapp.viewmodel.OrderViewModel
import com.quynhlm.dev.furnitureapp.viewmodel.ProductViewModel

class OrderScreen : ComponentActivity() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreenRun(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Order",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = FontFamily(Font(R.font.merriweather_regular))
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
                        onClick = { /* Handle action */ }) {
                        // Action icon (e.g., settings or more options) can be added here
                    }
                },
            )
        },
        content = { innerPadding ->
            showOrder(innerPadding)
        }
    )
}

@Composable
fun showOrder(innerPaddingValues: PaddingValues) {
    val orderViewModel: OrderViewModel = viewModel()
    val orderState = orderViewModel.orderState

    LaunchedEffect(Unit) {
        orderViewModel.getAllOrder()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, innerPaddingValues.calculateTopPadding(), end = 10.dp)
            .background(color = colorResource(id = R.color.background))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Delivered", fontSize = 20.sp, fontWeight = FontWeight(700))
                Divider(color = Color.Black, thickness = 4.dp, modifier = Modifier.width(40.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Processing",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight(700)
                )
                Text(text = "")
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Canceled",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight(700)
                )
                Text(text = "")
            }
        }
        Column {
            orderState?.let { state ->
                state.value?.data?.let { orders ->
                    LazyColumn {
                        items(orders) { order ->
                            Spacer(modifier = Modifier.height(20.dp))
                            val productViewModel: ProductViewModel = viewModel()
                            val productState = productViewModel.getAnProductState
                            LaunchedEffect(Unit) {
                                productViewModel.getAnProductByID(order.product_id)
                            }
                            productState.value?.data?.let { product ->
                                OrderItem(order, product , orderViewModel)
                            }
                        }
                    }
                } ?: run {
                    Log.e("TAG", "showOrder: orderState is null")
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order, product: Product , orderViewModel: OrderViewModel) {
    var showDialogDetails by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .shadow(elevation = 0.dp, shape = RoundedCornerShape(8.dp), clip = true)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Order No 3856231" + order.order_id,
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color.Black
            )
            Text(
                text = order.date,
                fontSize = 14.sp,
                fontWeight = FontWeight(500),
                color = Color.Gray
            )
        }
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                    )
                ) {
                    append("Quantity: ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                    )
                ) {
                    append("" + order.quantity)
                }
            })
            val totalPrice = product.price * order.quantity
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                    ) {
                        append("Total Amount: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("$ $totalPrice")
                    }
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButton(
                title = "Detail",
                modifier = Modifier
                    .width(100.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF242424))
                    .clickable(onClick = {
                        showDialogDetails = true
                    }),
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                    fontWeight = FontWeight(500),
                    fontSize = 15.sp,
                    color = Color.White
                ),
            )
            Text(
                text = if (order.state == 0) "Delivered" else "Canceled",
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = if (order.state == 0) Color("#27AE60".toColorInt()) else Color.Red
            )
        }
    }
    if (showDialogDetails == true) {
        DialogShowDetails(onDismissRequest = { showDialogDetails = false }, order, product , orderViewModel)
    }
}

@Composable
fun DialogShowDetails(onDismissRequest: () -> Unit, order: Order, product: Product , orderViewModel: OrderViewModel) {
    val  context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {

                }
                Image(
                    painter = painterResource(id = R.drawable.delete), contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            onDismissRequest()
                        }
                )
            }
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order No 3856231" + order.order_id,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color.Black
                )
                Text(
                    text = order.date,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color.Gray
                )
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )
                        ) {
                            append("Name: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(product.name)
                        }
                    }
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )
                        ) {
                            append("Price: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("$ " + product.price)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                        )
                    ) {
                        append("Quantity: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                        )
                    ) {
                        append("" + order.quantity)
                    }
                })
                val totalPrice = order.quantity * product.price
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )
                        ) {
                            append("Total Amount: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("$ $totalPrice")
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButton(
                    title = "Cancel",
                    modifier = Modifier
                        .width(100.dp)
                        .height(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if(order.state == 0) Color(0xFF242424) else Color.Gray)
                        .clickable(onClick = {
                            try {
                                order.state = 1
                                order.order_id?.let { orderViewModel.updateOrder(it, order) }
                                showMessage(context = context, "Cancle order  success fully")
                            }catch (e : Exception){
                                showMessage(context = context, "Cancle order not success fully")
                            }
                        }),
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                        fontWeight = FontWeight(500),
                        fontSize = 15.sp,
                        color = Color.White
                    ),
                )
                Text(
                    text = if (order.state == 0) "Delivered" else "Canceled",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = if (order.state == 0) Color("#27AE60".toColorInt()) else Color.Red
                )
            }
        }
    }
}