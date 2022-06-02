package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.GetUpdateStatusResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _listBorrower = MutableLiveData<List<GetUpdateStatusResponseItem>>()
    val listBorrower: LiveData<List<GetUpdateStatusResponseItem>> = _listBorrower

    fun getListBorrwer(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getRequestBorrower("jwt=$token")
            .enqueue(object: Callback<List<GetUpdateStatusResponseItem>>{
                override fun onResponse(
                    call: Call<List<GetUpdateStatusResponseItem>>,
                    response: Response<List<GetUpdateStatusResponseItem>>
                ) {
                    if(response.isSuccessful){
                        _listBorrower.postValue(response.body())
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<GetUpdateStatusResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }

            })
    }

}