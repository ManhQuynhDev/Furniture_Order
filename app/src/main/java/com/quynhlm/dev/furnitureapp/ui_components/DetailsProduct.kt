package com.quynhlm.dev.furnitureapp.ui_components

import Converters
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.quynhlm.dev.furnitureapp.Database.DB.Db_Helper
import com.quynhlm.dev.furnitureapp.Database.Repository.CartRepository
import com.quynhlm.dev.furnitureapp.Database.Repository.FavoriteRepository
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.models.Cart
import com.quynhlm.dev.furnitureapp.models.Favorite
import com.quynhlm.dev.furnitureapp.models.Product
import com.quynhlm.dev.furnitureapp.viewmodel.CartViewModel
import com.quynhlm.dev.furnitureapp.viewmodel.FavoriteViewModel
import kotlin.math.log

@Composable
fun DetailsProduct(product: Product, navController: NavController) {

    val context = LocalContext.current
    val db = Db_Helper.getInstance(context)
    val repository = CartRepository(db)
    var repositoryFavorite = FavoriteRepository(db)
    val cartViewModel = CartViewModel(repository)
    val favoriteViewModel = FavoriteViewModel(repositoryFavorite)
    val cartList by cartViewModel.getAllCarts().observeAsState(initial = emptyList())

    var quantityState by remember {
        mutableStateOf(1)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomHeaderDetails(product = product, navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 20.dp, start = 20.dp, top = 10.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = product.name.replace("+", " "),
                fontSize = 24.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily(Font(R.font.gelasio_bold))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "\$ " + product.price,
                    fontSize = 30.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold))
                )
                Row(
                    modifier = Modifier.width(113.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { quantityState = ++quantityState }
                            .clip(RoundedCornerShape(6.dp))
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
                        text = "" + quantityState,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily(
                            Font(R.font.nunitosans_7pt_condensed_bold)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                if (quantityState > 1) {
                                    quantityState = --quantityState
                                } else {
                                    showMessage(
                                        context = context,
                                        "quantity can not be less than 1"
                                    )
                                }
                            }
                            .clip(RoundedCornerShape(6.dp))
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
            Row(modifier = Modifier.width(200.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "" + product.rating,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily(
                        Font(R.font.nunitosans_7pt_condensed_bold)
                    ),
                    modifier = Modifier.padding(7.dp)
                )
                Text(
                    text = "(50 reviews)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color("#808080".toColorInt()),
                    fontFamily = FontFamily(
                        Font(R.font.nunitosans_7pt_condensed_bold)
                    ),
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .clickable {
                            navController.navigate("rating")
                        }
                )
            }
            Text(
                text = product.description.replace("+", " "),
                fontSize = 15.sp,
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight(500),
                color = Color("#606060".toColorInt()),
                fontFamily = FontFamily(
                    Font(R.font.nunitosans_7pt_condensed_light)
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            try {
                                val favorite = Favorite(0, product)
                                favoriteViewModel.addToFavorite(favorite)
                                showMessage(context, "add to cart successfully")
                            } catch (e: Exception) {
                                showMessage(context, "add to cart not successfully")
                            }
                        }
                        .shadow(elevation = 2.dp, RoundedCornerShape(8.dp))
                        .background(color = Color("#F5F5F5".toColorInt())),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.marker),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                }


                CustomButton(
                    title = "Add to cart", modifier = Modifier
                        .padding(7.dp)
                        .width(270.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF242424))
                        .clickable(onClick = {
                            var isCheck = false
                            for (cart in cartList){
                                if(cart.product.name.equals(product.name)){
                                    isCheck = true
                                    break
                                }
                            }
                            if(isCheck == true){
                                showMessage(context,"The product already exists in your cart")
                            }else{
                                try {
                                    val cart = Cart(null, product, quantityState)
                                    cartViewModel.addToCart(cart)
                                    showMessage(context, "add to cart successfully")
                                } catch (e: Exception) {
                                    showMessage(context, "add to cart not successfully")
                                }
                            }
                        }), textStyle = TextStyle(
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                        fontWeight = FontWeight(600),
                        fontSize = 17.sp
                    )
                )
            }
        }
    }
}

@Composable
fun CustomHeaderDetails(product: Product, navController: NavController) {
    var tint by remember {
        mutableStateOf(Color(0.2f, 0.2f, 0.2f, 0.5f))
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box {

                }
//                AsyncImage(
//                    model = product.image,
//                    contentDescription = null,
//                    placeholder = painterResource(id = R.drawable.imagedetails),
//                    modifier = Modifier
//                        .width(330.dp)
//                        .fillMaxHeight()
//                        .shadow(
//                            elevation = 2.dp,
//                            shape = RoundedCornerShape(bottomStart = 52.dp)
//                        )
//
//                        .zIndex(1f),
//                    contentScale = ContentScale.FillBounds
//                )

                val painter: Painter = rememberAsyncImagePainter(model = product.image)

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .width(330.dp)
                        .fillMaxHeight()
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(bottomStart = 52.dp)
                        )
                        .zIndex(1f),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Box(
            modifier = Modifier
                .width(125.dp)
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { navController.navigateUp() }
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(14.dp),
                            clip = true
                        )
                        .background(color = Color.White, RoundedCornerShape(14.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrowback),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Column(
                    modifier = Modifier
                        .height(192.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(40.dp),
                            clip = true
                        )
                        .width(64.dp)
                        .background(Color.White, shape = RoundedCornerShape(40.dp)),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.color1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(34.dp)
                            .clickable { tint = Color("#000000".toColorInt()) })

                    Image(
                        painter = painterResource(id = R.drawable.color2),
                        contentDescription = null,
                        modifier = Modifier.size(34.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.color3),
                        contentDescription = null,
                        modifier = Modifier.size(34.dp)
                    )
                }

                Row {

                }
            }
        }
    }
}