package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.detailpayment

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.PaymentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPaymentViewModel : ViewModel(){

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessful = MutableLiveData<String>()
    val isSuccessful: LiveData<String> = _isSuccessful

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun addPayment(token: String, id: String, payment: Int, method: String, context: Context){
        _isLoading.value = true
        _isSuccessful.value = "pending"
        ApiConfig.getApiService().postPayment("jwt=$token", id, method, payment)
            .enqueue(object : Callback<PaymentResponse>{
                override fun onResponse(
                    call: Call<PaymentResponse>,
                    response: Response<PaymentResponse>
                ) {
                    if (response.isSuccessful){
                        _isLoading.value = false
                        _isSuccessful.value = "success"
                        Toast.makeText(context, "Berhasil melakukan pembayaran", Toast.LENGTH_LONG).show()
                    } else {
                        _isLoading.value = false
                        _isSuccessful.value = "failed"
                        try {
                            val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                            val mErrorMessage = jsonObj.getString("message")
                            _errorMessage.value = mErrorMessage
                        } catch (e: Exception){
                            Toast.makeText(context, "Berhasil melakukan pembayaran", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isSuccessful.value = "failed"
                    Toast.makeText(context, "Berhasil melakukan pembayaran", Toast.LENGTH_LONG).show()
                }
            })
    }
}