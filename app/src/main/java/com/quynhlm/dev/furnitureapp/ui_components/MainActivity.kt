package com.quynhlm.dev.furnitureapp.ui_components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.models.Category
import com.quynhlm.dev.furnitureapp.models.Product
import com.quynhlm.dev.furnitureapp.ui.theme.FurnitureAppTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FurnitureAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}
@Composable
fun CategoryItem(category: Category, isSelected: Boolean, onChoose: () -> Unit) {
    val backgroundColor = if (isSelected) Color("#303030".toColorInt()) else Color("#F5F5F5".toColorInt())
    Column(
        modifier = Modifier.padding(end = 25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .size(44.dp)
                .clickable(onClick = onChoose)
                .shadow(elevation = 2.dp, RoundedCornerShape(14.dp))
                .background(color = backgroundColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = category.image,
                placeholder = painterResource(R.drawable.bag),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = category.name,
            color = Color("#999999".toColorInt()),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_light)
            )
        )
    }
}


//@Preview(name = "Register", showBackground = true)
@Composable
fun StyledText() {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = ParagraphStyle(
                lineHeight = 50.sp
            )
        ) {
            withStyle(
                style = SpanStyle(
                    color = Color.Gray,
                    fontSize = 27.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily(Font(R.font.gelasio_bold))
                )
            ) {
                append("Hello ! \n")
            }
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontSize = 27.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily(Font(R.font.gelasio_bold))
                )
            ) {
                append("WELCOME BACK")
            }
        }
    }

    BasicText(
        text = annotatedText,
        modifier = Modifier.width(300.dp),
    )
}
@Composable
fun ProductItem(product : Product , navController : NavController) {
    val productJson = Gson().toJson(product)
    val encodedProductJson = URLEncoder.encode(productJson, StandardCharsets.UTF_8.toString())
    Column(
        modifier = Modifier
            .width(165.dp)
            .height(253.dp)
            .clickable { navController.navigate("detail/$encodedProductJson") },
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                model = product.image,
                placeholder = painterResource(R.drawable.loading),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
                    .shadow(2.dp , shape = RoundedCornerShape(8.dp) , clip = true).background(Color.White)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp, end = 15.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Row {

                }
                Row(
                    modifier = Modifier
                        .size(30.dp)
                        .shadow(elevation = 2.dp, RoundedCornerShape(6.dp))
                        .background(color = Color(0.2f, 0.2f, 0.2f, 0.5f))
                        .alpha(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.shopping_bag),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
        Text(
            text = product.name,
            fontSize = 14.sp,
            color = Color("#606060".toColorInt()),
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_light)
            )
        )
        Text(
            text = "\$ " + product.price,
            fontSize = 14.sp,
            color = Color("#303030".toColorInt()),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_bold)
            )
        )
    }
}

