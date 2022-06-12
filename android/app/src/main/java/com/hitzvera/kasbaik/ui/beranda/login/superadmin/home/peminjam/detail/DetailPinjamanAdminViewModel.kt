package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.peminjam.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.DetailPinjamanAdminResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPinjamanAdminViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private lateinit var temp: List<DetailPinjamanAdminResponseItem>

    private var _list = MutableLiveData<DetailPinjamanAdminResponseItem>()
    val list: LiveData<DetailPinjamanAdminResponseItem> = _list

    fun getBorrowerById(token: String,id: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getBorrowerByID("jwt=$token", id)
            .enqueue(object: Callback<List<DetailPinjamanAdminResponseItem>> {
                override fun onResponse(
                    call: Call<List<DetailPinjamanAdminResponseItem>>,
                    response: Response<List<DetailPinjamanAdminResponseItem>>
                ) {
                    if (response.isSuccessful){
                        temp = response.body()!!
                        _list.postValue(temp[0])
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<DetailPinjamanAdminResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}