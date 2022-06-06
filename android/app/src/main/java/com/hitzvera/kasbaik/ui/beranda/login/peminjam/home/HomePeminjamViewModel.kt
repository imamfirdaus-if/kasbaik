package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.HomeUserResponse
import com.hitzvera.kasbaik.response.Profile
import com.hitzvera.kasbaik.response.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePeminjamViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse

    fun reqProfilePeminjam(context: Context, token: String){
        _isLoading.value = true
        ApiConfig.getApiService().getRequestProfile("jwt=$token")
            .enqueue(object: Callback<Profile>{
                override fun onResponse(
                    call: Call<Profile>,
                    response: Response<Profile>
                ) {
                    if(response.isSuccessful){
                        _profileResponse.postValue(response.body()?.profile)
                        _isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    Toast.makeText(context, "profile response failed to fetch", Toast.LENGTH_SHORT).show()
                }

            })
    }

}