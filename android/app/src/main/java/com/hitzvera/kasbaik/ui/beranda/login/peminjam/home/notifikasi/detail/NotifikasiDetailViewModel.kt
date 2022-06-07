package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.MessageItem
import com.hitzvera.kasbaik.response.Profile
import com.hitzvera.kasbaik.response.UpdateHasUpdateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifikasiDetailViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _message = MutableLiveData<MessageItem>()
    val message: LiveData<MessageItem> = _message

    fun updateHasRead(token: String, idMessage: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().updateHasReadMessage("jwt=$token", idMessage)
            .enqueue(object: Callback<UpdateHasUpdateResponse>{
                override fun onResponse(
                    call: Call<UpdateHasUpdateResponse>,
                    response: Response<UpdateHasUpdateResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _message.postValue(response.body()?.message)
                    } else {
                        Toast.makeText(context, "failed update the data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UpdateHasUpdateResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed update the data", Toast.LENGTH_SHORT).show()
                }

            })
    }

}