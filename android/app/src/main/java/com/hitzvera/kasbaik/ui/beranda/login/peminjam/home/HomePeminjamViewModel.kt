package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.HomeUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePeminjamViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _homeUserResponse = MutableLiveData<HomeUserResponse>()
    val homeUserResponse: LiveData<HomeUserResponse> = _homeUserResponse

    fun reqHomePeminjam(context: Context, token: String){
        _isLoading.value = true
        ApiConfig.getApiService().requestHomePeminjam("jwt=$token")
            .enqueue(object: Callback<HomeUserResponse> {
                override fun onResponse(
                    call: Call<HomeUserResponse>,
                    response: Response<HomeUserResponse>
                ) {
                    if(response.isSuccessful){
                        _homeUserResponse.postValue(response.body())
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<HomeUserResponse>, t: Throwable) {
                    Toast.makeText(context, "home response failed to fetch", Toast.LENGTH_SHORT).show()
                }

            })
    }
}