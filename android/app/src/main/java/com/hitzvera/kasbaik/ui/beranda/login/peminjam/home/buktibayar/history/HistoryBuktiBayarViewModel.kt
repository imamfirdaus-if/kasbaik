package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.history

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.MessageItem
import com.hitzvera.kasbaik.response.MessagesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryBuktiBayarViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _buktiBayarResponse = MutableLiveData<List<MessageItem>>()
    val buktiBayarResponse: LiveData<List<MessageItem>> = _buktiBayarResponse

    fun getBuktiBayar(token: String, context: Context){
        ApiConfig.getApiService().getBuktiBayar("jwt=$token")
            .enqueue(object : Callback<MessagesResponse> {
                override fun onResponse(
                    call: Call<MessagesResponse>,
                    response: Response<MessagesResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _buktiBayarResponse.postValue(response.body()?.message)
                    } else {
                        Toast.makeText(context, "Failed fething data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<MessagesResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Failed fething data", Toast.LENGTH_LONG).show()
                }

            })
    }

}