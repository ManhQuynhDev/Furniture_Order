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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.quynhlm.dev.furnitureapp.Database.DB.Db_Helper
import com.quynhlm.dev.furnitureapp.Database.Repository.CartRepository
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.models.Cart
import com.quynhlm.dev.furnitureapp.models.Product
import com.quynhlm.dev.furnitureapp.viewmodel.CartViewModel

@Composable
fun CartScreen(innerPadding: PaddingValues, navHostController: NavController) {
    val context = LocalContext.current
    val db = Db_Helper.getInstance(context)
    val repository = CartRepository(db)
    val cartViewModel = CartViewModel(repository)
    val cartList by cartViewModel.getAllCarts().observeAsState(initial = emptyList())
    val totalAmount = remember(cartList) {
        calculateTotalPrice(cartList)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 10.dp,
                innerPadding.calculateTopPadding(),
                end = 10.dp
            )
            .background(color = colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CartGrid(carts = cartViewModel.getAllCarts() , cartViewModel)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                   TextField(
                       placeholder = {
                           Text(
                               text = "Enter your promo code",
                               color = Color("#999999".toColorInt()),
                               fontSize = 16.sp,
                               fontFamily = FontFamily(
                                   Font(R.font.nunitosans_7pt_condensed_light)
                               ),
                               fontWeight = FontWeight(600)
                           )
                       },
                       modifier = Modifier
                           .fillMaxWidth(),
                       value = "",
                       onValueChange = {

                       },
                       colors = TextFieldDefaults.colors(
                           focusedContainerColor = Color("#E0E0E0".toColorInt()),
                           unfocusedContainerColor = Color.White,
                           disabledContainerColor = Color.White,
                           unfocusedIndicatorColor = Color.White,
                       ),
                   )
                Spacer(modifier = Modifier.height(10.dp))
                   Row(
                       modifier = Modifier
                           .size(45.dp)
                           .shadow(elevation = 2.dp, RoundedCornerShape(14.dp))
                           .background(color = Color("#303030".toColorInt())),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.Center,
                   ) {
                       Image(
                           painter = painterResource(id = R.drawable.arrownext),
                           contentDescription = null,
                           modifier = Modifier.size(22.dp)
                       )
                   }
               }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total:",
                    fontSize = 23.sp,
                    fontWeight = FontWeight(700),
                    color = Color("#808080".toColorInt()),
                    fontFamily = FontFamily(
                        Font(R.font.nunitosans_7pt_condensed_bold)
                    )
                )
                Text(
                    text = "\$ " + totalAmount,
                    fontSize = 23.sp,
                    fontWeight = FontWeight(700),
                    color = Color("#000000".toColorInt()),
                    fontFamily = FontFamily(
                        Font(R.font.nunitosans_7pt_condensed_bold)
                    )
                )
            }
            Box(
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF242424))
                    .clickable(onClick = {
                        navHostController.navigate("checkout/${1}")
                    })
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Check Box",
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

@Composable
fun CartGrid(carts: LiveData<List<Cart>> , cartViewModel : CartViewModel) {
    val cartList by carts.observeAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(cartList) { cartItem ->
            CartItem(cartItem , cartViewModel)
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = colorResource(id = R.color.graySecond), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun CartItem( cart: Cart , cartViewModel: CartViewModel){
    val context = LocalContext.current
    val product : Product = cart.product
    var quantityState by remember {
        mutableStateOf(cart.quantity)
    }
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(110.dp)
        .background(color = colorResource(id = R.color.background)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){

        AsyncImage(model = product.image, contentDescription = null,
            modifier = Modifier
                .width(110.dp)
                .height(120.dp)
                .shadow(2.dp, shape = RoundedCornerShape(8.dp), clip = true), contentScale = ContentScale.FillBounds)

        Column (modifier = Modifier
            .width(200.dp)
            .padding(start = 10.dp)
            .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = product.name.replace("+" , " "), fontSize = 15.sp, fontWeight = FontWeight(600), color = colorResource(
                    id = R.color.gray
                ), fontFamily = FontFamily(
                    Font(R.font.nunitosans_7pt_condensed_light)
                ))
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "\$ "+ product.price, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(
                    Font(R.font.nunitosans_7pt_condensed_bold)
                ))
            }
            Row(
                modifier = Modifier.width(113.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { quantityState = ++quantityState }
                        .background(color = Color("#E0E0E0".toColorInt())),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                }
                Text(
                    text = "" + quantityState ,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily(
                        Font(R.font.nunitosans_7pt_condensed_bold)
                    )
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .clickable {
                            if (quantityState < 1) {
                                showMessage(context = context, "Not success when quantity < 1")
                            }
                        }
                        .background(color = Color("#E0E0E0".toColorInt())),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.apart),
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
        Column (modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally){
            Icon(painter = painterResource(id = R.drawable.delete), contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { cartViewModel.deleteCart(cart = cart) })
            Row {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppCart(navHostController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "My Cart",
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
        CartScreen(innerPadding = innerPadding, navHostController = navHostController)
    }
}

fun calculateTotalPrice(cartList: List<Cart>): Float {
    var totalPrice = 0.0f
    if(cartList.size == 0){
        totalPrice = 0.0f
    }else{
        for (cartItem in cartList) {
            totalPrice += cartItem.product.price * cartItem.quantity
        }
    }
    return totalPrice
}
