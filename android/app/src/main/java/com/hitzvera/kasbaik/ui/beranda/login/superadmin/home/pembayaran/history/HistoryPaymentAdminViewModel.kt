package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pembayaran.history

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.HistoryPaymentAdminResponse
import com.hitzvera.kasbaik.response.ListPaymentAdminResponseItem
import com.hitzvera.kasbaik.response.TablePaymentItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HistoryPaymentAdminViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _list = MutableLiveData<List<TablePaymentItem>>()
    val list: LiveData<List<TablePaymentItem>> = _list

    fun getHistoryPayment(token: String, idBorrower: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getPaymentByID("jwt=$token", idBorrower)
            .enqueue(object: Callback<HistoryPaymentAdminResponse> {
                override fun onResponse(
                    call: Call<HistoryPaymentAdminResponse>,
                    response: Response<HistoryPaymentAdminResponse>
                ) {
                    if (response.isSuccessful){
                        _list.postValue(response.body()?.tablePayment)
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<HistoryPaymentAdminResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}