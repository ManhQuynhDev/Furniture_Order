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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.quynhlm.dev.furnitureapp.Database.DB.Db_Helper
import com.quynhlm.dev.furnitureapp.Database.Repository.CartRepository
import com.quynhlm.dev.furnitureapp.Database.Repository.FavoriteRepository
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.models.Cart
import com.quynhlm.dev.furnitureapp.models.Favorite
import com.quynhlm.dev.furnitureapp.models.Product
import com.quynhlm.dev.furnitureapp.viewmodel.CartViewModel
import com.quynhlm.dev.furnitureapp.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val db = Db_Helper.getInstance(context)
    val cartRepository = CartRepository(db)
    val repository = FavoriteRepository(db)
    val cartViewModel = CartViewModel(cartRepository)
    val favoriteViewModel = FavoriteViewModel(repository)

    val favoriteList by favoriteViewModel.getAllFavorite().observeAsState(initial = emptyList())

    Column(modifier = Modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
            items(favoriteList) { favorite ->
                FavoriteItem(
                    favoriteItem = favorite , favoriteViewModel = favoriteViewModel ,cartViewModel = cartViewModel
                )
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = colorResource(id = R.color.graySecond), thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))
            }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                CustomButton(
                    title = "Add all to my cart", modifier = Modifier
                        .padding(7.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF242424))
                        .clickable(onClick = {}), textStyle = TextStyle(
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
fun FavoriteItem(favoriteItem : Favorite , favoriteViewModel: FavoriteViewModel , cartViewModel: CartViewModel){
    val context = LocalContext.current
    val product = favoriteItem.product
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(110.dp)
        .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){

        AsyncImage(model = product.image, contentDescription = null ,
            modifier = Modifier
                .width(110.dp)
                .height(120.dp)
                .shadow(2.dp,shape = RoundedCornerShape(8.dp),clip = true), contentScale = ContentScale.FillBounds)

        Column (modifier = Modifier
            .width(200.dp)
            .padding(start = 10.dp)
            .fillMaxHeight()) {
            Text(text = product.name.replace("+" , " "), fontSize = 15.sp, fontWeight = FontWeight(600), color = colorResource(
                id = R.color.gray
            ), fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_light)
            ))
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "\$ " + product.price, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_bold)
            ))
        }
        Column (modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally){
            Icon(painter = painterResource(id = R.drawable.delete), contentDescription = null, modifier = Modifier.size(24.dp).clickable {
                favoriteViewModel.deleteFavorite(favoriteItem)
            })

            Row {
                IconButton(onClick = {
                    val cart = Cart(null , favoriteItem.product , 1)
                    cartViewModel.addToCart(cart)
                    favoriteViewModel.deleteFavorite(favoriteItem)
                    showMessage(context = context , "Add to cart successfully")
                }) {
                    Icon(painter = painterResource(id = R.drawable.bag), contentDescription = null, modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}