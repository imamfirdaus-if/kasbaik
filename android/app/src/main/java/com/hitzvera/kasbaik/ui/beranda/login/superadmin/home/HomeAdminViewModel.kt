package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.HomeAdminResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAdminViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _list = MutableLiveData<HomeAdminResponse>()
    val list: LiveData<HomeAdminResponse> = _list

    fun getSummary(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getSummary("jwt=$token")
            .enqueue(object: Callback<HomeAdminResponse>{
                override fun onResponse(
                    call: Call<HomeAdminResponse>,
                    response: Response<HomeAdminResponse>
                ) {
                    if (response.isSuccessful){
                        _list.postValue(response.body())
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<HomeAdminResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}