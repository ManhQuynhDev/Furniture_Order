package com.quynhlm.dev.furnitureapp.ui_components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.core.validation.EmptyValid
import com.quynhlm.dev.furnitureapp.models.User
import com.quynhlm.dev.furnitureapp.viewmodel.LoginViewModel
@Composable
fun LoginScreen(navController: NavController?) {
    val context = LocalContext.current
    val emptyValid = EmptyValid()
    val loginViewModel: LoginViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToolbarCustom()
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
                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var passwordVisible by remember { mutableStateOf(false) }
                //Username input
                InputUsername(
                    title = "Username",
                    username = username,
                    onUsernameChange = { username = it })
                //Password input
                InputPassWord(
                    title = "PassWord",
                    password = password,
                    onPasswordChange = { password = it },
                    visible = passwordVisible,
                    onPasswordVisible = { passwordVisible = !it })
                Text(
                    text = "Forgot Password",
                    color = Color("#303030".toColorInt()),
                    fontSize = 17.sp,
                    fontWeight = FontWeight(600)
                )
                CustomButton(
                    title = "Login",
                    modifier = Modifier
                        .padding(7.dp)
                        .width(285.dp)
                        .height(50.dp)
                        .shadow(elevation = 5.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF242424))
                        .clickable(onClick = {
                            if (!emptyValid.isEmptyLogin(
                                    username = username,
                                    password = password
                                )
                            ) {
                                showMessage(
                                    context = context,
                                    message = "Please username and password not empty"
                                )
                            } else {
                                val user = User()
                                user.username = username
                                user.password = password
                                if (loginViewModel.loginAccount(user)) {
                                    showMessage(context, "Create account successfully")
                                } else {
                                    showMessage(context, "Create account not successfully")
                                }
                            }
                        }),
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.gelasio_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 18.sp,
                        color = Color.White,
                    ),
                )
                Text(
                    text = "SIGN UP",
                    modifier = Modifier.clickable {
                        navController!!.navigate("signup")
                    }
                )
            }
        }
        Column {}
    }
}