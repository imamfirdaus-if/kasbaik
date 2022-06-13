package com.hitzvera.kasbaik.ui.beranda.login.mitra.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.SummaryMitraResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeMitraViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _summaryMitraResponse = MutableLiveData<SummaryMitraResponse>()
    val summaryMitraResponse: LiveData<SummaryMitraResponse> = _summaryMitraResponse


    fun getMitraSummary(token: String, idMitra: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getSummaryMitra("jwt=$token", idMitra)
            .enqueue(object : Callback<SummaryMitraResponse> {
                override fun onResponse(
                    call: Call<SummaryMitraResponse>,
                    response: Response<SummaryMitraResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _summaryMitraResponse.postValue(response.body())
                    } else {
                        Toast.makeText(context, "Gagal mendapatkan data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<SummaryMitraResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Gagal mendapatkan data", Toast.LENGTH_LONG).show()
                }
            })
    }

}