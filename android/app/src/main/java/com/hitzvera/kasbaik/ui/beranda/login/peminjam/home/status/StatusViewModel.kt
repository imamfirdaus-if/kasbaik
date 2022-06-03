package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.response.PaymentResponse
import com.hitzvera.kasbaik.response.TablePaymentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _listBorrowing = MutableLiveData<List<Borrower>>()
    val listBorrowing: LiveData<List<Borrower>> = _listBorrowing

    private var _listPayment = MutableLiveData<List<TablePaymentResponse>>()
    val listPayment: LiveData<List<TablePaymentResponse>> = _listPayment

    fun getListBorrowing(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getRequestBorrowing("jwt=$token")
            .enqueue(object: Callback<List<Borrower>>{
                override fun onResponse(
                    call: Call<List<Borrower>>,
                    response: Response<List<Borrower>>
                ) {
                    _isLoading.value = false
                   if(response.isSuccessful){
                       _listBorrowing.postValue(response.body())
                   } else {
                       Toast.makeText(context, "Failed fetch data", Toast.LENGTH_LONG).show()
                   }
                }

                override fun onFailure(call: Call<List<Borrower>>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Failed fetch data", Toast.LENGTH_LONG).show()
                }

            })
    }


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