package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.buktibayar

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.MessagesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailBuktiBayarViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateBuktiBayar(token: String, idMessage: String, isAccepted: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().updateBuktiBayar("jwt=$token", idMessage, isAccepted)
            .enqueue(object: Callback<MessagesResponse> {
                override fun onResponse(
                    call: Call<MessagesResponse>,
                    response: Response<MessagesResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        Toast.makeText(context, "data berhasil diedit", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "data gagal diedit", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<MessagesResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Server terjadi masalah", Toast.LENGTH_LONG).show()
                }

            })
    }

}