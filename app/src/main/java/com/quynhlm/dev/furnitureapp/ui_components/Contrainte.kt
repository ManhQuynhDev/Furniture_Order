package com.quynhlm.dev.furnitureapp.ui_components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.quynhlm.dev.furnitureapp.R
//Custom button
@Composable
fun CustomButton(
    title: String,
    modifier: Modifier,
    textStyle: TextStyle,
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = textStyle
            )
        }
    }
}
//Customer Input
@Composable
fun InputText(title: String, textState: MutableState<String>) {
    Column {
        Text(
            text = title,
            color = colorResource(id = R.color.graySecond),
            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
        TextField(
            value = textState.value,
            onValueChange = { newText ->
                textState.value = newText
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
}

@Composable
fun InputPassWord(
    title: String,
    password: MutableState<String>,
    passwordVisible: MutableState<Boolean>
) {
    Column {
        Text(
            text = title,
            color = colorResource(id = R.color.graySecond),
            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light)),
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color("#E0E0E0".toColorInt()),
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.Gray,
                unfocusedIndicatorColor = Color.Gray,
            ),
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image =
                    if (passwordVisible.value)
                        painterResource(id = R.drawable.hide)
                    else
                        painterResource(id = R.drawable.view)
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            })
    }
}

fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}