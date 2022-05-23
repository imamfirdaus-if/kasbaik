package com.hitzvera.kasbaik.ui.beranda.login.peminjam

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAsPeminjamViewModel: ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _token = MutableLiveData<LoginResponse>()
    val token: LiveData<LoginResponse> = _token

    fun loginUser(email: String, password: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().requestLogin(email, password)
            .enqueue(object: Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.isSuccessful){
                        _token.postValue(response.body())
                        _isLoading.value = false
                    } else {
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        Toast.makeText(context, jsonObj.getString("status"), Toast.LENGTH_LONG).show()
                        _isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Failed to login please try again later", Toast.LENGTH_SHORT).show()
                }

            })
    }
}