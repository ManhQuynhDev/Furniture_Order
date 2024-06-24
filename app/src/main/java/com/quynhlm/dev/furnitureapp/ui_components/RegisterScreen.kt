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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.core.validation.EmptyValid
import com.quynhlm.dev.furnitureapp.models.User
import com.quynhlm.dev.furnitureapp.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    var emptyValid = EmptyValid()

    val registerViewModel: RegisterViewModel = viewModel()
    val registerState by registerViewModel.registerState

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

                var username by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var confirmPassword by remember { mutableStateOf("") }
                var passwordVisible by remember { mutableStateOf(false) }

                //Username
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

                //Email
                Column {
                    Text(
                        text = "Email",
                        color = colorResource(id = R.color.graySecond),
                        fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    TextField(
                        value = email,
                        onValueChange = { newText ->
                            email = newText
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

                Column {
                    Text(
                        text = "Confirm",
                        color = colorResource(id = R.color.graySecond),
                        fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    TextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
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

                CustomButton(
                    title = "SIGN UP",
                    modifier = Modifier
                        .padding(7.dp)
                        .width(285.dp)
                        .height(50.dp)
                        .shadow(elevation = 5.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF242424))
                        .clickable(onClick = {
                            if (!emptyValid.isValid(username, email, password, confirmPassword)) {
                                showMessage(
                                    context = context,
                                    "Please fill in all the fields correctly."
                                )
                            } else {
                                val isSame = emptyValid.isTheSame(
                                    password = password,
                                    confirm = confirmPassword
                                )
                                if (isSame) {
                                    showMessage(
                                        context = context,
                                        "Confirm password is not the same"
                                    )
                                } else {
                                    val user = User()
                                    user.username = username
                                    user.password = password

                                    registerViewModel.registerUser(user)
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
                }, modifier = Modifier.clickable { navController.popBackStack() })
            }
        }
        Column {
            registerState?.let {
                Log.e("TAG", "RegisterScreen: " + it.message)
                if (it.status.equals("ok")) {
                    showMessage(context, "Create account successfully")
                } else {
                    showMessage(context, "Create account not successfully")
                }
            }
        }
    }
}
