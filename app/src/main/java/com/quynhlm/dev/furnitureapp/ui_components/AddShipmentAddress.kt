package com.quynhlm.dev.furnitureapp.ui_components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.quynhlm.dev.furnitureapp.R
import com.quynhlm.dev.furnitureapp.models.District
import com.quynhlm.dev.furnitureapp.models.DistrictRequest
import com.quynhlm.dev.furnitureapp.models.Province
import com.quynhlm.dev.furnitureapp.models.Shipment
import com.quynhlm.dev.furnitureapp.models.Ward
import com.quynhlm.dev.furnitureapp.viewmodel.ShipmentAddressViewModel
import com.quynhlm.dev.furnitureapp.viewmodel.ShipmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddShipmentScreen(navController : NavController , shipmentUpdate : Shipment? , type : Int?){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = if (type == 0) "Add shipping address" else "Update shipping address",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier.fillMaxWidth(),
                            fontFamily = FontFamily(
                                Font(R.font.merriweather_regular)
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp()}) {
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
                        }
                    },
                )
            },
            content = { innerPadding ->
                AddShipment(innerPadding ,shipmentUpdate, type)
            }
        )
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddShipment(innerPaddingValues: PaddingValues?,shipmentUpdate : Shipment? , type : Int?) {
    // Context
    val context = LocalContext.current
    // ViewModel
    val shipmentAddressViewModel: ShipmentAddressViewModel = viewModel()
    val shipmentViewModel : ShipmentViewModel = viewModel()
    val shipmentState = shipmentViewModel.shipmentState
    // States
    var expanded by remember { mutableStateOf(false) }
    var expandedDistrict by remember { mutableStateOf(false) }
    var expandedWard by remember { mutableStateOf(false) }
    var selectedProvince by remember { mutableStateOf(Province(ProvinceID =  0 , ProvinceName = if(type == 0) "" else shipmentUpdate!!.province.replace("+" , " "))) }
    var selectedDistrict by remember { mutableStateOf(District(DistrictID = 0 , ProvinceID = 0 , DistrictName = if(type == 0) "" else shipmentUpdate!!.district.replace("+" , " "))) }
    var selectedWard by remember { mutableStateOf(if(type == 0) "" else shipmentUpdate!!.ward.replace("+" , " "))}

    var full_name by remember {
        mutableStateOf(if(type == 0) "" else shipmentUpdate!!.full_name.replace("+" ," "))
    }
    var address by remember {
        mutableStateOf(if(type == 0) "" else shipmentUpdate!!.address.replace("+" ," "))
    }
    var zipcode by remember {
        mutableStateOf(if(type == 0) "" else shipmentUpdate!!.zipcode.replace("+" ," "))
    }

    LaunchedEffect(Unit) {
        shipmentAddressViewModel.fetchProvinces()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(start = 10.dp, innerPaddingValues!!.calculateTopPadding(), end = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedTextField(
                value = full_name,
                label = { Text(text = "Full name") },
                placeholder = { Text(text = "Ex: Bruno Pham") },
                onValueChange = {full_name = it},
                modifier = Modifier.fillMaxWidth(),
            )

            // Address field
            OutlinedTextField(
                value = address,
                label = { Text(text = "Address") },
                placeholder = { Text(text = "Ex: 25 Robert Latouche Street") },
                onValueChange = {address = it},
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = zipcode,
                label = { Text(text = "Zipcode (Postal Code)") },
                placeholder = { Text(text = "012345") },
                onValueChange = {zipcode = it},
                modifier = Modifier.fillMaxWidth()
            )
            // Country dropdown
            DropdownFieldProvince(
                label = "Province",
                selectedText = selectedProvince.ProvinceName,
                expanded = expanded,
                onExpandedChange = { expanded = it },
                onItemSelected = { selectedProvince = it },
                items = shipmentAddressViewModel.provincesState?.value?.data?.filterNotNull() ?: emptyList()
            )

            val districtRequest = DistrictRequest(selectedProvince.ProvinceID)
            shipmentAddressViewModel.fetchDistricts(districtRequest)

            DropdownFieldDistrict(
                label = "District",
                selectedText = selectedDistrict.DistrictName,
                expanded = expandedDistrict,
                onExpandedChange = { expandedDistrict = it },
                onItemSelected = { selectedDistrict = it },
                items = shipmentAddressViewModel.districtsState?.value?.data?.filterNotNull() ?: emptyList()
            )

            shipmentAddressViewModel.fetchWards(selectedDistrict.DistrictID)
            // District dropdown
            DropdownFieldWard(
                label = "Ward",
                selectedText = selectedWard,
                expanded = expandedWard,
                onExpandedChange = { expandedWard = it },
                onItemSelected = { selectedWard = it },
                items = shipmentAddressViewModel.wardsState?.value?.data?.filterNotNull() ?: emptyList()
            )
        }
        // Save button
        Column {
            CustomButton(
                title = "SAVE ADDRESS",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF242424))
                    .clickable(onClick = {
                        if(type == 0){
                            val newShipment = Shipment(
                                null,
                                full_name,
                                address,
                                zipcode,
                                selectedProvince.ProvinceName,
                                selectedDistrict.DistrictName,
                                selectedWard,
                                1
                            )
                            shipmentViewModel.insertShipment(newShipment)
                        }else{
                            shipmentUpdate!!.full_name = full_name
                            shipmentUpdate!!.address = address
                            shipmentUpdate!!.zipcode = zipcode
                            shipmentUpdate!!.province = selectedProvince.ProvinceName
                            shipmentUpdate!!.district = selectedDistrict.DistrictName
                            shipmentUpdate!!.ward = selectedWard
                            shipmentUpdate!!.user_id = 1
                            shipmentViewModel.updateShipment(shipmentUpdate.shipment_id!!,shipmentUpdate)
                        }
                    }),
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_bold)),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = Color.White
                ),
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
        LaunchedEffect(key1 = shipmentState.value) {
            shipmentState.value?.let {
                Log.e("TAG", "RegisterScreen: " + it.status)
                if (it.status == "ok") {
                    showMessage(context, if(type == 0) "Add shipment successfully" else "Update shipment successfully")
                } else {
                    showMessage(context, if(type == 0) "Add shipment not successfully" else "Update shipment not successfully")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownFieldProvince(
    label: String,
    selectedText: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onItemSelected: (Province) -> Unit,
    items: List<Province>
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                label = { Text(text = label) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),

            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.ProvinceName) },
                        onClick = {
                            onItemSelected(item)
                            onExpandedChange(false)
                            showMessage(context = context, "Item :" + item.ProvinceName)
                        }
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownFieldDistrict(
    label: String,
    selectedText: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onItemSelected: (District) -> Unit,
    items: List<District>?
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                label = { Text(text = label) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                items?.forEach { item ->
                    item?.let { district ->
                        DropdownMenuItem(
                            text = { Text(text = district.DistrictName) },
                            onClick = {
                                onItemSelected(district)
                                onExpandedChange(false)
                                showMessage(context = context , "Item :" + district.DistrictName)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownFieldWard(
    label: String,
    selectedText: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onItemSelected: (String) -> Unit,
    items: List<Ward>?
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                label = { Text(text = label) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                items?.forEach { item ->
                    item?.let { ward ->
                        DropdownMenuItem(
                            text = { Text(text = ward.WardName) },
                            onClick = {
                                onItemSelected(ward.WardName)
                                onExpandedChange(false)
                                showMessage(context = context , "Item :" + ward.WardName)
                            }
                        )
                    }
                }
            }
        }
    }
}
