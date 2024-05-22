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
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.quynhlm.dev.furnitureapp.ui.theme.FurnitureAppTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

sealed class Screen(val route: String, val icon: Int, val title: String) {
    object Home : Screen("home", R.drawable.home, "Trang chủ")
    object History : Screen("history",R.drawable.marker, "Lịch sử")
    object Cart : Screen("cart", R.drawable.bell, "Giỏ hàng")
    object Profile : Screen("profile",R.drawable.person, "Hồ sơ")
}

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
                    SmallTopAppBarExample()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.white),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = buildAnnotatedString {
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
                    }, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
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
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.cart),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        NavigationGraph(navController = navController , innerPadding)
    }
}

@Composable
private fun BottomNavigationBar(navController: NavHostController) {

    // Tạo list dựa vào các object đã khai báo ở main
    val items = listOf(
        Screen.Home,
        Screen.History,
        Screen.Cart,
        Screen.Profile
    )

    NavigationBar(
        containerColor = Color("#ffffff".toColorInt())
    ) {
        // trả về thông tin của màn hình hiện tại( đường dẫn ,trạng thái màn hình,Trạng thái vòng đời của màn hình,..)
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route
        // kiểm tra xem các mục trong điều hướng có trùng với đường dẫn màn hình đc lưu trong biến currentRoute hay ko

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = screen.icon),
                        contentDescription = screen.title,
                        modifier = Modifier.size(24.dp)
                    )
                },

                selected = currentRoute == screen.route, // nếu danh mục được chọn, trả về currentRoute= link đường dẫn đến danh mục được chọn
                onClick = {
                    navController.navigate(screen.route) {
                        // Điều hướng đến một màn hình duy nhất, không tạo thêm bản sao
                        launchSingleTop = true
                        // Khôi phục trạng thái đã lưu
                        restoreState = true
                        // Xóa tất cả các trang trước trang đích để tránh chồng chất trang
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = android.R.color.holo_orange_dark),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = colorResource(id = android.R.color.holo_orange_dark),
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {

//         nhận nhiệm vụ xử lí chính cho việc chuyển màn khi ấn vào icon của thanh điều hướng
    // truyền và o 3 tham số : navController là công cụ thuộc thư việ có sẵn của jetpack compose giúp xử lí về bottom nav bar
    NavHost(navController, startDestination = Screen.Home.route) {

        // tùy vào route object nào sẽ navigate đến fun component đó
        composable(Screen.Home.route) { HomeScreen(innerPadding) }
        composable(Screen.History.route) { LoginScreen() }
        composable(Screen.Cart.route) { RegisterScreen() }
        composable(Screen.Profile.route) { WelComeScreen() }

    }
}

@Preview(name = "WelCome", showBackground = true)
@Composable
fun WelComeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(400.dp)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "MAKE YOUR",
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.gray),
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily(Font(R.font.gelasio_bold))
                )
                Text(
                    text = "HOME BEAUTIFUL",
                    fontSize = 30.sp,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily(Font(R.font.gelasio_bold))
                )
                Text(
                    modifier = Modifier.padding(start = 25.dp, top = 15.dp),
                    text = "The best simple place where you discover most wonderful furnitures and make your home beautiful",
                    color = colorResource(id = R.color.graySecond),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                    lineHeight = 35.sp,
                    textAlign = TextAlign.Justify
                )
            }
            Column {
                CustomRow(onClick = { /*TODO*/ })
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun CustomRow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(7.dp)
            .width(160.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFF242424))
            .clickable(onClick = onClick)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),  // Fill the size of the Box
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Get Started",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.gelasio_bold)),
                fontWeight = FontWeight(600),
                fontSize = 18.sp
            )  // Set text color
        }
    }
}

@Preview(name = "Login", showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Divider(
                    color = Color("#BDBDBD".toColorInt()),
                    thickness = 2.dp,
                    modifier = Modifier.width(105.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(75.dp)
                )
                Divider(
                    color = Color("#BDBDBD".toColorInt()),
                    thickness = 2.dp,
                    modifier = Modifier.width(105.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            StyledText()
        }
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(450.dp)
                .clip(RoundedCornerShape(16.dp))
                .shadow(elevation = 4.dp, spotColor = colorResource(id = R.color.graySecond)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp), verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputRow(title = "Email")
                InputRowPass(title = "PassWord")
                Text(
                    text = "Forgot Password",
                    color = Color("#303030".toColorInt()),
                    fontSize = 17.sp,
                    fontWeight = FontWeight(600)
                )
                Box(
                    modifier = Modifier
                        .padding(7.dp)
                        .width(285.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF242424))
                        .clickable(onClick = {})
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Login",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.gelasio_bold)),
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp
                        )  // Set text color
                    }
                }
                Text(text = "SIGN UP")
            }

        }
        Column {

        }
    }
}

@Composable
fun InputRow(title: String) {
    Column {
        Text(
            text = title,
            color = colorResource(id = R.color.graySecond),
            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
        CustomTextField()
    }
}

@Composable
fun InputRowPass(title: String) {
    Column {
        Text(
            text = title,
            color = colorResource(id = R.color.graySecond),
            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
        PasswordTextField()
    }
}

@Composable
fun CategoryItem(icon: Int, name: String) {
    Column(
        modifier = Modifier.padding(end = 25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .size(44.dp)
                .shadow(elevation = 2.dp, RoundedCornerShape(14.dp))
                .background(color = Color("#F5F5F5".toColorInt())),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = name,
            color = Color("#999999".toColorInt()),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_light)
            )
        )
    }
}

@Composable
fun CustomTextField() {
    TextField(
        value = "",
        onValueChange = {
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color("#E0E0E0".toColorInt()),
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
        ),
    )
}

@Preview(name = "Register", showBackground = true)
@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(13.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Divider(
                    color = Color("#BDBDBD".toColorInt()),
                    thickness = 2.dp,
                    modifier = Modifier.width(105.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(75.dp)
                )
                Divider(
                    color = Color("#BDBDBD".toColorInt()),
                    thickness = 2.dp,
                    modifier = Modifier.width(105.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "WelCome",
                fontSize = 28.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily(Font(R.font.gelasio_bold))
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(530.dp)
                .clip(RoundedCornerShape(16.dp))
                .shadow(elevation = 4.dp, spotColor = colorResource(id = R.color.graySecond))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp), verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputRow(title = "Name")
                InputRow(title = "Email")
                InputRowPass(title = "Password")
                InputRowPass(title = "Confirm PassWord")

                Box(
                    modifier = Modifier
                        .padding(7.dp)
                        .width(285.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF242424))
                        .clickable(onClick = {})
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "SIGN UP",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp
                        )
                    }
                }
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light))
                        )
                    ) {
                        append("Already have account? ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light))
                        )
                    ) {
                        append("SIGN IN")
                    }
                })
            }
        }
        Column {

        }
    }
}

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
fun PasswordTextField() {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { password = it },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color("#E0E0E0".toColorInt()),
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image =
                if (passwordVisible)
                    painterResource(id = R.drawable.hide)
                else
                    painterResource(id = R.drawable.view)
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = image, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
    )
}

private class Category(val icon: Int, val name: String)
private class Product(val image: Int, val name: String, val price: Double)

@Composable
fun HomeScreen(innerPadding: PaddingValues = PaddingValues()) {
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

    Column(
        modifier = Modifier.padding(
            top = innerPadding.calculateTopPadding() + 20.dp,
            end = 20.dp,
            start = 20.dp
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
                            price = product.price
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(image : Int, name : String, price: Double) {
    Column(
        modifier = Modifier
            .width(165.dp)
            .height(253.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = image),
                contentDescription = null
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
                        .background(color = Color("#95a5a6".toColorInt()))
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
            text = name,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_light)
            )
        )
        Text(
            text = "\$ " + price,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(
                Font(R.font.nunitosans_7pt_condensed_light)
            )
        )
    }
}

@Preview(name = "Main", showBackground = true , device = "id:pixel_2_xl")
@Composable
fun GreetingPreview() {
    FurnitureAppTheme {
        SmallTopAppBarExample()
    }
}

@Preview(name = "Details", showBackground = true , device = "id:pixel_2_xl")
@Composable
fun DetailsProduct(){
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Đặt background cho Box
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        // Add any content you want in the left column
                    }
                    Image(
                        painter = painterResource(id = R.drawable.imagedetails),
                        contentDescription = null,
                        modifier = Modifier
                            .width(355.dp)
                            .fillMaxHeight()
                            .shadow(
                                elevation = 2.dp,
                                shape = RoundedCornerShape(bottomStart = 52.dp)
                            )
                            .zIndex(1f),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Box(
                    modifier = Modifier.size(50.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrowback),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}