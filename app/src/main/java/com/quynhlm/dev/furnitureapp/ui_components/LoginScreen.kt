package com.quynhlm.dev.furnitureapp.ui_components

import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.quynhlm.dev.furnitureapp.viewmodel.RegisterViewModel

@Preview(showBackground = true , device = "id:pixel_4a")
@Composable
fun demo(){
    LoginScreen(navController = null)
}

@Composable
fun LoginScreen(navController: NavController?){
    val context = LocalContext.current
    val emptyValid : EmptyValid = EmptyValid()

    val loginViewModel: LoginViewModel = viewModel()
    val loginState by loginViewModel.loginState
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
                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var passwordVisible by remember { mutableStateOf(false) }

                Column {
                    Text(
                        text = "Username",
                        color = colorResource(id = R.color.graySecond),
                        fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    TextField(
                        value = username,
                        onValueChange = { newText ->
                            username = newText
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

                //Password

                Column {
                    Text(
                        text = "PassWord",
                        color = colorResource(id = R.color.graySecond),
                        fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
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
                                Icon(
                                    painter = image,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        })
                }

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
                            if (!emptyValid.isEmptyLogin(username = username, password = password)) {
                                showMessage(
                                    context = context,
                                    message = "Please username and password not empty"
                                )
                            } else {
                                val  user = User()
                                user.username = username
                                user.password = password
                                loginViewModel.loginAccount(user)
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
        Column {
            loginState?.let {
                Log.e("TAG", "LoginScreen: " + it.status)
                Log.e("TAG", "LoginScreen: " + it.message)
                Log.e("TAG", "LoginScreen: " + it.data)
                if (it.data == true) {
                    showMessage(context, "Login successfully")
                    navController!!.navigate("home")
                } else {
                    showMessage(context, "Login not successfully")
                }
            }
        }
    }
}