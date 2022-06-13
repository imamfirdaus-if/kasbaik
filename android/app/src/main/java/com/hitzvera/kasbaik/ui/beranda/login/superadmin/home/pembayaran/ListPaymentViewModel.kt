package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pembayaran

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.ListPaymentAdminResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPaymentViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _list = MutableLiveData<List<ListPaymentAdminResponseItem>>()
    val list: LiveData<List<ListPaymentAdminResponseItem>> = _list

    fun getListPayment(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getListPayment("jwt=$token")
            .enqueue(object: Callback<List<ListPaymentAdminResponseItem>> {
                override fun onResponse(
                    call: Call<List<ListPaymentAdminResponseItem>>,
                    response: Response<List<ListPaymentAdminResponseItem>>
                ) {
                    if (response.isSuccessful){
                        _list.postValue(response.body())
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ListPaymentAdminResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}