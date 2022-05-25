package com.hitzvera.kasbaik.ui.beranda.daftar

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.UserResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterViewModel: ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessful = MutableLiveData<String>()
    val isSuccessful: LiveData<String> = _isSuccessful

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun createAccount(
        username: String,
        email: String,
        phone: String,
        password: String,
        role: String,
        context: Context
    ) {
        _isLoading.value = true
        _isSuccessful.value = "pending"
        ApiConfig.getApiService().requestCreateAcountUser(username, email, phone, password, role)
            .enqueue(object: Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(context, "Sucessfully make an account", Toast.LENGTH_SHORT).show()
                        Log.e("REGISTER", response.body().toString())
                        _isLoading.value = false
                        _isSuccessful.value = "success"
                    } else {
                        _isLoading.value = false
                        _isSuccessful.value = "failed"
                        try {
                            val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                            val mErrorMessage = jsonObj.getString("message")
                            _errorMessage.value = mErrorMessage
                            Toast.makeText(
                                context,
                                mErrorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed make an account", Toast.LENGTH_SHORT).show()
                    _isLoading.value = false
                    _isSuccessful.value = "failed"
                }

            })
    }
}