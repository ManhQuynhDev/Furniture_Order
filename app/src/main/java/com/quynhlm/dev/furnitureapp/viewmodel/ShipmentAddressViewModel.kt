package com.quynhlm.dev.furnitureapp.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quynhlm.dev.furnitureapp.Response.ResponseGHN
import com.quynhlm.dev.furnitureapp.models.District
import com.quynhlm.dev.furnitureapp.models.DistrictRequest
import com.quynhlm.dev.furnitureapp.models.Province
import com.quynhlm.dev.furnitureapp.models.Ward
import com.quynhlm.dev.furnitureapp.services.GHNInstance
import com.quynhlm.dev.furnitureapp.services.GHNService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShipmentAddressViewModel : ViewModel() {
    private val ghnService: GHNService = GHNInstance.apiService

    private val _provincesState = mutableStateOf<ResponseGHN<List<Province?>?>?>(null)
    val provincesState: State<ResponseGHN<List<Province?>?>?> = _provincesState

    private val _districtsState = mutableStateOf<ResponseGHN<List<District?>?>?>(null)
    val districtsState: State<ResponseGHN<List<District?>?>?> = _districtsState

    private val _wardsState = mutableStateOf<ResponseGHN<List<Ward?>?>?>(null)
    val wardsState: State<ResponseGHN<List<Ward?>?>?> = _wardsState

    fun fetchProvinces() {
        viewModelScope.launch {
            ghnService.getListProvince()?.enqueue(object : Callback<ResponseGHN<List<Province?>?>?> {
                override fun onResponse(
                    call: Call<ResponseGHN<List<Province?>?>?>,
                    response: Response<ResponseGHN<List<Province?>?>?>
                ) {
                    if (response.isSuccessful) {
                        _provincesState.value = response.body()
                    } else {
                        _provincesState.value = null
                    }
                }

                override fun onFailure(call: Call<ResponseGHN<List<Province?>?>?>, t: Throwable) {
                    _provincesState.value = null
                }
            })
        }
    }

    fun fetchDistricts(districtRequest: DistrictRequest) {
        viewModelScope.launch {
            ghnService.getListDistrict(districtRequest)?.enqueue(object : Callback<ResponseGHN<List<District?>?>?> {
                override fun onResponse(
                    call: Call<ResponseGHN<List<District?>?>?>,
                    response: Response<ResponseGHN<List<District?>?>?>
                ) {
                    if (response.isSuccessful) {
                        _districtsState.value = response.body()
                    } else {
                        _districtsState.value = null
                    }
                }

                override fun onFailure(call: Call<ResponseGHN<List<District?>?>?>, t: Throwable) {
                    _districtsState.value = null
                }
            })
        }
    }

    fun fetchWards(districtId: Int) {
        viewModelScope.launch {
            ghnService.getListWard(districtId)?.enqueue(object : Callback<ResponseGHN<List<Ward?>?>?> {
                override fun onResponse(
                    call: Call<ResponseGHN<List<Ward?>?>?>,
                    response: Response<ResponseGHN<List<Ward?>?>?>
                ) {
                    if (response.isSuccessful) {
                        _wardsState.value = response.body()
                    } else {
                        _wardsState.value = null
                    }
                }

                override fun onFailure(call: Call<ResponseGHN<List<Ward?>?>?>, t: Throwable) {
                    _wardsState.value = null
                }
            })
        }
    }
}
