package com.quynhlm.dev.furnitureapp.ui_components

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.models.Category
import com.quynhlm.dev.furnitureapp.models.Product
import com.quynhlm.dev.furnitureapp.models.Shipment
import com.quynhlm.dev.furnitureapp.viewmodel.CategoryViewModel
import com.quynhlm.dev.furnitureapp.viewmodel.ProductViewModel

data class BottomNavigationItem(
    val title: String,
    val selectIcon: ImageVector,
    var unselectItem: ImageVector
)

val productArr = listOf(
    Product(R.drawable.image1, "Black Simple Lamp", "",12.00F,1,"2",3.3F),
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
//            composable("detail") { DetailsProduct(navController) }
            composable(
                "detail/{productJson}",
                arguments = listOf(navArgument("productJson") { type = NavType.StringType })
            ) { backStackEntry ->
                val productJson = backStackEntry.arguments?.getString("productJson")
                val product = Gson().fromJson(productJson, Product::class.java)
                DetailsProduct(product , navController)
            }
            composable("cart") { SmallTopAppCart(navController) }

            composable(
                "checkout/{shipment_id}",
                arguments = listOf(navArgument("shipment_id") { type = NavType.IntType })
            ) { backStackEntry ->
                val shipmentId = backStackEntry.arguments?.getInt("shipment_id")
                SmallTopAppBarPayment(navController,shipmentId)
            }

            composable("success") { FinalScreen(navController) }
            composable("order") { OrderScreenRun(navController) }

            composable(
                "addShipment/{shipmentJson}/{type}",
                arguments = listOf(
                    navArgument("shipmentJson") { type = NavType.StringType },
                    navArgument("type") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val shipmentJson = backStackEntry.arguments?.getString("shipmentJson")
                val type = backStackEntry.arguments?.getInt("type")
                val shipment = Gson().fromJson(shipmentJson, Shipment::class.java)
                AddShipmentScreen(navController, shipment, type)
            }
//            composable("addShipment") { AddShipmentScreen(navController) }
            composable("addPayment") { AddPaymentScreen(navController) }
            composable("paymentMethod") { SelectPaymentScreen(navController) }
            composable("setting") { settingScreens(navController) }
            composable("selectShipment") { AddressScreen(navController) }
            composable("myReview") { MyReViewTopBar(navController) }
            composable("rating") { ReView(navController) }
        }
    }

    @Composable
    fun FurnitureApp(navHostController: NavController) {
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
                    TopAppBar(navController = navController, navHostController = navHostController)
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
                NavigationGraph(
                    navHostController = navHostController,
                    navController = navController,
                    innerPadding = innerPadding
                )
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
    fun TopAppBar(navController: NavHostController, navHostController: NavController) {
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
                IconButton(onClick = { navHostController.navigate("cart") }) {
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
    fun NavigationGraph(
        navHostController: NavController,
        navController: NavHostController,
        innerPadding: PaddingValues
    ) {
        NavHost(
            navController,
            startDestination = "Home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Home") { HomeScreen(innerPadding, navHostController) }
            composable("Favorite") { FavoriteScreen(innerPadding) }
            composable("Notification") { NotificationScreen(innerPadding) }
            composable("Profile") { AccountScreenControl(innerPadding, navHostController) }
        }
    }

    @Composable
    fun HomeScreen(innerPadding: PaddingValues = PaddingValues(), navController: NavController) {
        val scrollState = rememberScrollState()
        val context = LocalContext.current

        Column(
            modifier = Modifier.padding(
                top = 10.dp,
                end = 15.dp,
                start = 15.dp
            )
        ) {
            //Category List
            val categoryViewModel: CategoryViewModel = viewModel()
            val categoryState by categoryViewModel.categoryState
            var productViewModel: ProductViewModel = viewModel()
            val productState by productViewModel.productState
            var selectedCategory by remember { mutableStateOf<Category?>(null) }

            LaunchedEffect(Unit) {
                categoryViewModel.getAllCategory()
                productViewModel.getAllProduct()
            }
            categoryState?.let {
                if (it.data != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(scrollState)
                    ) {
                        it.data.forEach { category ->
                            CategoryItem(
                                category = category,
                                isSelected = category == selectedCategory,
                                onChoose = { selectedCategory = category }
                            )
                        }
                    }
                } else {
                    showMessage(context, "Call not successfully")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            productState?.let {
                if (it.data != null) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(it.data.chunked(2)) { productRow ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                for (product in productRow) {
                                    ProductItem(
                                        product = product,
                                        navController = navController
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Log.e("TAG", "HomeScreen: Call Product not successfully")
                }
            }
        }
    }
}