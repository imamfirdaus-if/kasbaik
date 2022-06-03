package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.PaymentResponse
import com.hitzvera.kasbaik.response.tablePaymentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _listPayment = MutableLiveData<List<tablePaymentResponse>>()
    val listPayment: LiveData<List<tablePaymentResponse>> = _listPayment

    fun getListPayment(token: String, context: Context, id: String){
        _isLoading.value = true
        ApiConfig.getApiService().getPayment("jwt=$token", id)
            .enqueue(object : Callback<PaymentResponse>{
                override fun onResponse(
                    call: Call<PaymentResponse>,
                    response: Response<PaymentResponse>
                ) {
                    if (response.isSuccessful){
                        _isLoading.value = false
                        _listPayment.postValue(response.body()?.tablePayment)
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }

}