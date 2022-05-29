package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.detailpinjaman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.PostUpdataStatusResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPinjamanViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessful = MutableLiveData<String>()
    val isSuccessful: LiveData<String> = _isSuccessful

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun updateStatusRequest(token: String, status: String, idBorrower: String){
        _isLoading.value = true
        _isSuccessful.value = "pending"
        ApiConfig.getApiService().postRequestUpdateStatus("jwt=$token", status, idBorrower)
            .enqueue(object : Callback<PostUpdataStatusResponse>{
                override fun onResponse(
                    call: Call<PostUpdataStatusResponse>,
                    response: Response<PostUpdataStatusResponse>
                ) {
                    if(response.isSuccessful){
                        _isLoading.value = false
                        _isSuccessful.value = "success"
                    } else {
                        _isLoading.value = false
                        _isSuccessful.value = "failed"
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        val mErrorMessage = jsonObj.getString("message")
                        _errorMessage.value = mErrorMessage
                    }
                }

                override fun onFailure(call: Call<PostUpdataStatusResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isSuccessful.value = "failed"
                }

            })
    }

}