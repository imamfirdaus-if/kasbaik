package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.UpdateHasUpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart

class UploadBuktiBayarViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessful = MutableLiveData<String>()
    val isSuccessful: LiveData<String> = _isSuccessful

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun postBuktiBayar(
        token: String,
        file: MultipartBody.Part,
        nominal: RequestBody,
        message: RequestBody,
        context: Context
    ){
        _isSuccessful.value = "pending"
        _isLoading.value = true
        ApiConfig.getApiService().postBuktiBayar("jwt=$token", file, nominal, message)
            .enqueue(object: Callback<UpdateHasUpdateResponse>{
                override fun onResponse(
                    call: Call<UpdateHasUpdateResponse>,
                    response: Response<UpdateHasUpdateResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _isSuccessful.value = "success"
                    } else {
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

                override fun onFailure(call: Call<UpdateHasUpdateResponse>, t: Throwable) {
                    Toast.makeText(context, "failed posting bukti bayar", Toast.LENGTH_LONG).show()
                }

            })
    }

}