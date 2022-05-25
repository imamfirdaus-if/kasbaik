package com.hitzvera.kasbaik.ui.beranda.login.mitra

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAsMitraViewModel: ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _token = MutableLiveData<LoginResponse>()
    val token: LiveData<LoginResponse> = _token

    private var _isSuccessful = MutableLiveData<String>()
    val isSuccessful: LiveData<String> = _isSuccessful

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loginMitra(email: String, password: String, context: Context){
        _isLoading.value = true
        _isSuccessful.value = "pending"
        ApiConfig.getApiService().requestLogin(email, password)
            .enqueue(object: Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.isSuccessful){
                        _token.postValue(response.body())
                        _isLoading.value = false
                        _isSuccessful.value = "success"
                    } else {
                        _isSuccessful.value = "failed"
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        val mErrorMessage = jsonObj.getString("message")
                        _errorMessage.value = mErrorMessage
                        Toast.makeText(context, mErrorMessage , Toast.LENGTH_SHORT).show()
                        _isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isSuccessful.value = "failed"
                    Toast.makeText(context, "Failed to login please try again later", Toast.LENGTH_SHORT).show()
                }

            })
    }

}