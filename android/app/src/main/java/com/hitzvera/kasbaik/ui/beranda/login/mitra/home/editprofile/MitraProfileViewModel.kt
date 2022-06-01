package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.editprofile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.GetMitraProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MitraProfileViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _mitraProfileResponse = MutableLiveData<GetMitraProfileResponse>()
    val mitraProfileResponse: LiveData<GetMitraProfileResponse> = _mitraProfileResponse

    fun getMitraProfile(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getRequestMitraProfile("jwt=$token")
            .enqueue(object: Callback<GetMitraProfileResponse>{
                override fun onResponse(
                    call: Call<GetMitraProfileResponse>,
                    response: Response<GetMitraProfileResponse>
                ) {
                    if(response.isSuccessful){
                        _isLoading.value = false
                        _mitraProfileResponse.postValue(response.body())
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed fetching data", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<GetMitraProfileResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed fetching data", Toast.LENGTH_LONG).show()
                }

            })
    }


}