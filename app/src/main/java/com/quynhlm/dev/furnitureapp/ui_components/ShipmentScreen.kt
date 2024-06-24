package com.quynhlm.dev.furnitureapp.ui_components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.models.Shipment
import com.quynhlm.dev.furnitureapp.viewmodel.ProductViewModel
import com.quynhlm.dev.furnitureapp.viewmodel.ShipmentViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Shipping address",
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = FontFamily(
                            Font(R.font.merriweather_regular)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
                        onClick = { /* Handle action */ }) {
                        // Action icon (e.g., settings or more options) can be added here
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val shipment = Shipment(null, "", "", "", "", "", "", 1)
                    val shipmentJson = Gson().toJson(shipment)
                    val encodedShipmentJson =
                        URLEncoder.encode(shipmentJson, StandardCharsets.UTF_8.toString())
                    navController.navigate("addShipment/${encodedShipmentJson}/${0}")
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        content = { innerPadding ->
            SelectAddress(innerPadding, navController)
        }
    )
}

@Composable
fun SelectAddress(innerPaddingValues: PaddingValues, navController: NavController) {
    var shipmentViewModel: ShipmentViewModel = viewModel()
    val shipmentState by shipmentViewModel.shipmentState

    LaunchedEffect(Unit) {
        shipmentViewModel.getAllShipment()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(start = 10.dp, innerPaddingValues.calculateTopPadding(), end = 10.dp)
    ) {
        shipmentState?.data?.let { data ->
            val listShipment = data.toMutableList()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(listShipment) { item ->
                    Spacer(modifier = Modifier.height(5.dp))
                    ItemAddress(item, navController, shipmentViewModel)
                }
            }
        } ?: run {
            Text(text = "No data available")
        }
    }
}

@Composable
fun ItemAddress(
    shipment: Shipment,
    navController: NavController,
    shipmentViewModel: ShipmentViewModel
) {
    var chooseShipment by remember {
        mutableStateOf(false)
    }
    var openAlertDialog by remember { mutableStateOf(false) }
    var shipmentState = shipmentViewModel.shipmentState
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = chooseShipment, onCheckedChange = {
                chooseShipment = it
                navController.navigate("checkout/${shipment.shipment_id}")
            })
            Text(
                text = "Use as the shipping address",
                fontSize = 18.sp, fontWeight = FontWeight(400)
            )
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = shipment.full_name.replace("+", " "),
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily(
                            Font(R.font.nunitosans_7pt_condensed_bold)
                        )
                    )
                    val shipmentJson = Gson().toJson(shipment)
                    val encodedShipmentJson =
                        URLEncoder.encode(shipmentJson, StandardCharsets.UTF_8.toString())
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.edit),
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    navController.navigate("addShipment/${encodedShipmentJson}/${1}")
                                },
                            contentDescription = null
                        )
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    openAlertDialog = true
                                },
                            contentDescription = null
                        )
                    }
                    if (openAlertDialog) {
                        AlertDialogExample(
                            onDismissRequest = { openAlertDialog = false },
                            onConfirmation = {
                                try {
                                    shipment.shipment_id?.let { shipmentViewModel.deleteShipment(it) }
                                    showMessage(context,"Delete successfully")
                                } catch (e: Exception) {
                                    showMessage(context,"Delete not successfully")
                                }
                            },
                            dialogTitle = "Config Delete Shipment",
                            dialogText = "Do you want to delete your address ?"
                        )
                    }
                }
                Divider()
                Column(modifier = Modifier.padding(start = 15.dp)) {
                    Text(
                        text = shipment.address.replace("+", " ") + ", " + shipment.zipcode,
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(
                            Font(R.font.nunitosans_7pt_condensed_bold)
                        ),
                        fontWeight = FontWeight(500)
                    )
                    Text(
                        text = shipment.ward.replace(
                            "+",
                            " "
                        ) + ", " + shipment.district.replace(
                            "+",
                            " "
                        ) + ", " + shipment.province.replace("+", " "),
                        fontSize = 15.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily(
                            Font(R.font.nunitosans_7pt_condensed_bold)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
