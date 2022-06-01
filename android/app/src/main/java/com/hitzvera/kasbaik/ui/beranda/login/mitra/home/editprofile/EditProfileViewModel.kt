package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.editprofile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.GetMitraProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessful = MutableLiveData<String>()
    val isSuccessful: LiveData<String> = _isSuccessful

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun postEditMitraProfile(token: String, file: MultipartBody.Part, name: RequestBody, location: RequestBody, phone: RequestBody,context: Context){
        _isLoading.value = true
        _isSuccessful.value = "pending"
        ApiConfig.getApiService().postRequestMitraProf("jwt=$token", file, name, location, phone)
            .enqueue(object: Callback<GetMitraProfileResponse>{
                override fun onResponse(call: Call<GetMitraProfileResponse>, response: Response<GetMitraProfileResponse>) {
                    if(response.isSuccessful){
                        _isLoading.value = false
                        _isSuccessful.value = "success"
                    } else {
                        _isLoading.value = false
                        _isSuccessful.value = "failed"
                        Toast.makeText(context, "Failed post the data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<GetMitraProfileResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isSuccessful.value = "failed"
                    Toast.makeText(context, "Failed post the data", Toast.LENGTH_LONG).show()
                }

            })
    }

}