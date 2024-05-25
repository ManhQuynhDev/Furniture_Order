package com.quynhlm.dev.furnitureapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.quynhlm.dev.furnitureapp.models.Product

data class BottomNavigationItem(
    val title: String,
    val selectIcon: ImageVector,
    var unselectItem: ImageVector
)

val productArr = listOf(
    Product(R.drawable.image1, "Black Simple Lamp", 12.00),
    Product(R.drawable.image2, "Black Simple Lamp", 12.00),
    Product(R.drawable.image3, "Black Simple Lamp", 12.00),
    Product(R.drawable.image1, "Black Simple Lamp", 12.00),
    Product(R.drawable.image1, "Black Simple Lamp", 12.00),
    Product(R.drawable.image2, "Black Simple Lamp", 12.00),
    Product(R.drawable.image3, "Black Simple Lamp", 12.00),
    Product(R.drawable.image1, "Black Simple Lamp", 12.00),
)

class MainControl : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MyApp()
        }
    }

    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "welcome") {
            composable("welcome") { WelComeScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("home") { FurnitureApp(navController) }
            composable("signup") { RegisterScreen(navController) }
            composable("detail") { DetailsProduct(navController) }
            composable("cart") { SmallTopAppCart(navController) }
            composable("checkout") { SmallTopAppBarPayment(navController) }
            composable("success") { FinalScreen(navController) }
            composable("order") { OrderScreenRun(navController) }
            composable("addShipment") {AddShipmentScreen(navController) }
            composable("addPayment") { AddPaymentScreen(navController) }
            composable("paymentMethod") { SelectPaymentScreen(navController) }
            composable("setting") { settingScreens(navController) }
            composable("selectShipment") { AddressScreen(navController) }
            composable("myReview") { MyReViewTopBar(navController) }
            composable("rating") { ReView(navController) }
        }
    }

    @Composable
    fun FurnitureApp(navHostController : NavController) {
        val navController = rememberNavController()
        val items = listOf(
            BottomNavigationItem("Home", Icons.Default.Home, Icons.Outlined.Home),
            BottomNavigationItem("Favorite", Icons.Default.Favorite, Icons.Outlined.Favorite),
            BottomNavigationItem(
                "Notification",
                Icons.Default.Notifications,
                Icons.Outlined.Notifications
            ),
            BottomNavigationItem("Profile", Icons.Default.Person, Icons.Outlined.Person)
        )
        var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(navController = navController , navHostController = navHostController)
                },
                bottomBar = {
                    BottomNavigationBar(
                        items = items,
                        selectedItemIndex = selectedItemIndex,
                        onItemSelected = { index ->
                            selectedItemIndex = index
                            navController.navigate(items[index].title)
                        }
                    )
                }
            ) { innerPadding ->
                NavigationGraph(navHostController = navHostController,navController = navController, innerPadding = innerPadding)
            }
        }
    }

    @Composable
    fun BottomNavigationBar(
        items: List<BottomNavigationItem>,
        selectedItemIndex: Int,
        onItemSelected: (Int) -> Unit
    ) {
        NavigationBar(
            containerColor = Color("#ffffff".toColorInt())
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = { onItemSelected(index) },
                    icon = {
                        Icon(
                            imageVector = if (selectedItemIndex == index) item.selectIcon else item.unselectItem,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(id = android.R.color.black),
                        unselectedIconColor = Color.Gray
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(navController: NavHostController , navHostController: NavController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: "Home"
        val homeTitle = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily(Font(R.font.gelasio_bold))
                )
            ) {
                append("Make home\n")
            }
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily(Font(R.font.gelasio_bold))
                )
            ) {
                append("BEAUTIFUL")
            }
        }

        val title: Any = when (currentRoute) {
            "Home" -> homeTitle
            "Favorite" -> "Favorite"
            "Notification" -> "Notification"
            "Profile" -> "Profile"
            else -> "Furniture App"
        }
        TopAppBar(
            title = {
                if (title is AnnotatedString) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (title is String) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = FontFamily(
                            Font(R.font.merriweather_regular)
                        )
                    )
                }
            },
            actions = {
                IconButton(onClick = { navHostController.navigate("cart")}) {
                    Icon(
                        painter = painterResource(id = R.drawable.cart),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
        )
    }

    @Composable
    fun NavigationGraph(navHostController : NavController,navController: NavHostController, innerPadding: PaddingValues) {
        NavHost(
            navController,
            startDestination = "Home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Home") { HomeScreen(innerPadding , navHostController) }
            composable("Favorite") { FavoriteScreen(innerPadding) }
            composable("Notification") { NotificationScreen(innerPadding) }
            composable("Profile") { AccountScreenControl(innerPadding , navHostController) }
        }
    }

    @Composable
    fun HomeScreen(innerPadding: PaddingValues = PaddingValues() , navController: NavController) {
        val categoryArr = listOf(
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair"),
            Category(R.drawable.cart, "Chair")
        )
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.padding(
                top = 10.dp,
                end = 15.dp,
                start = 15.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
            ) {
                categoryArr.forEach { category ->
                    CategoryItem(icon = category.icon, name = category.name)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(productArr.chunked(2)) { productRow ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (product in productRow) {
                            ProductItem(
                                image = product.image,
                                name = product.name,
                                price = product.price,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun FavoriteScreen(innerPadding: PaddingValues) {
        Column(modifier = Modifier) {
            FavoriteGrid(productArr = productArr)
        }
    }

    @Composable
    fun FavoriteGrid(productArr: List<Product>) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(productArr) { productRow ->
                    FavoriteItem(
                        icon = productRow.image,
                        name = productRow.name,
                        price = productRow.price
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = colorResource(id = R.color.graySecond), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
                contentAlignment = Alignment.BottomEnd) {
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
            CartScreen(innerPadding = innerPadding , navHostController = navHostController)
        }
    }
    @Composable
    fun PaymentScreen(innerPadding: PaddingValues , navHostController : NavController) {
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
                            modifier = Modifier.size(24.dp).clickable { navHostController.navigate("selectShipment") }
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
                        Text(
                            text = "Bruno Fernandes",
                            modifier = Modifier.padding(start = 15.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily(
                                Font(R.font.nunitosans_7pt_condensed_bold)
                            )
                        )
                        Divider(
                            color = Color.Gray,
                            thickness = 1.5.dp
                        )

                        Column(modifier = Modifier.padding(start = 15.dp)) {
                            Text(
                                text = "25 rue Robert Latouche, Nice, 06200, Côte",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.nunitosans_7pt_condensed_bold)
                                ),
                                fontWeight = FontWeight(500)
                            )
                            Text(
                                text = "D’azur, France", fontSize = 14.sp, fontFamily = FontFamily(
                                    Font(R.font.nunitosans_7pt_condensed_bold)
                                )
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
                            modifier = Modifier.size(24.dp).clickable { navHostController.navigate("paymentMethod") }
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
                                text = "\$ 95.00",
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
                                text = "\$ 5.00",
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
                                text = "\$ 100.00",
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
                            navHostController.navigate("success")
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

//    @Preview(showBackground = true , device = "id:pixel_4a")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SmallTopAppBarPayment(navHostController: NavController) {
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
            PaymentScreen(innerPadding , navHostController)
        }
    }

    @Composable
    fun NotificationScreen(innerPadding: PaddingValues) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background))
        ) {
            NotificationGrid(productArr = productArr)
        }
    }

    @Composable
    fun CartScreen(innerPadding: PaddingValues , navHostController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 10.dp,
                    innerPadding.calculateTopPadding(),
                    end = 10.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CartGrid(productArr = productArr)
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
                    contentAlignment = Alignment.TopEnd
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
                        text = "\$ 95.00",
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
                            navHostController.navigate("checkout")
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
    fun CartGrid(productArr: List<Product>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(productArr) { productRow ->
                CartItem(icon = productRow.image, name = productRow.name, price = productRow.price)
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = colorResource(id = R.color.graySecond), thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

    @Composable
    fun NotificationGrid(productArr: List<Product>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(productArr) { productRow ->
                NotificationItem()
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
