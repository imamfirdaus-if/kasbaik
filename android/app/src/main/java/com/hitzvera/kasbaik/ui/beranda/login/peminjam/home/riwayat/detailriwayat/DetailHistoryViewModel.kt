package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat.detailriwayat

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHistoryViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _listBorrowing = MutableLiveData<List<Borrower>>()
    val listBorrowing: LiveData<List<Borrower>> = _listBorrowing

    private var _listPayment = MutableLiveData<List<PaymentItem>>()
    val listPayment: LiveData<List<PaymentItem>> = _listPayment

    fun getListBorrowing(token: String, context: Context, idBorrower: String){
        _isLoading.value = true
        ApiConfig.getApiService().getBorrowing("jwt=$token", idBorrower)
            .enqueue(object: Callback<GetBorrowerByIdResponse>{
                override fun onResponse(
                    call: Call<GetBorrowerByIdResponse>,
                    response: Response<GetBorrowerByIdResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _listBorrowing.postValue(response.body()?.pinjaman)
                    } else {
                        Toast.makeText(context, "Failed fetch data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<GetBorrowerByIdResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Failed fetch data", Toast.LENGTH_LONG).show()
                }

            })
    }


    fun getListPayment(token: String, context: Context, id: String){
        _isLoading.value = true
        ApiConfig.getApiService().getPaymentById("jwt=$token", id)
            .enqueue(object : Callback<GetPaymentResponseUser>{
                override fun onResponse(
                    call: Call<GetPaymentResponseUser>,
                    response: Response<GetPaymentResponseUser>
                ) {
                    if (response.isSuccessful){
                        _isLoading.value = false
                        _listPayment.postValue(response.body()?.payment)
                        Log.e("CEK", response.body().toString())
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<GetPaymentResponseUser>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }

}