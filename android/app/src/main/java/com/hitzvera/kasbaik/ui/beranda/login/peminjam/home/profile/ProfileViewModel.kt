package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.profile
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileViewModel: ViewModel() {
    private var _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProfile(context: Context, token: String){
        _isLoading.value = true
        ApiConfig.getApiService().getRequestProfile("jwt=$token")
            .enqueue(object: Callback<ProfileResponse>{
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if(response.isSuccessful){
                        _profileResponse.postValue(response.body())
                        _isLoading.value = false
                    }

                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed fetch the data", Toast.LENGTH_SHORT).show()
                }

            })
    }
}