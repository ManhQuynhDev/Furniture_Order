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
import com.quynhlm.dev.furnitureapp.core.validation.EmailFormat
import com.quynhlm.dev.furnitureapp.core.validation.EmptyValid
import com.quynhlm.dev.furnitureapp.models.User
import com.quynhlm.dev.furnitureapp.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    var emptyValid = EmptyValid()
    val emailFormat = EmailFormat()

    val registerViewModel: RegisterViewModel = viewModel()
//    val registerState by registerViewModel.registerState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(13.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ToolbarCustom()
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
                //Username input
                InputUsername(title = "Username", username = username , onUsernameChange = { username = it })
                //Email input
                InputUsername(title = "Email", username = email , onUsernameChange = { email = it })
                //Password input
                InputPassWord(title = "PassWord",password = password, onPasswordChange = {password = it},visible = passwordVisible , onPasswordVisible = {passwordVisible = !it})
                //Confirm input
                InputPassWord(title = "Confirm",password = confirmPassword, onPasswordChange = {confirmPassword = it},visible = passwordVisible , onPasswordVisible = {passwordVisible = !it})
                //Button Click
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
                                //Check email
                                if(!emailFormat.isEmailFormat(email)){
                                    showMessage(
                                        context = context,
                                        "Email is not format"
                                    )
                                }
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

                                    if(registerViewModel.registerUser(user)){
                                        showMessage(context, "Create account successfully")
                                    }else{
                                        showMessage(context, "Create account not successfully")
                                    }
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
        Column {}
    }
}
