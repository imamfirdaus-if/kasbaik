package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.pinjamdana

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.PostBorrowerResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinjamDanaViewModel: ViewModel(){

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessful = MutableLiveData<String>()
    val isSuccessful: LiveData<String> = _isSuccessful

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var _postBorrowerResponse = MutableLiveData<PostBorrowerResponse>()
    val postBorrowerResponse: LiveData<PostBorrowerResponse> = _postBorrowerResponse

    fun postBorrower(
        token: String,
        loanAmount: Int,
        reason: String,
        monthlyIncome: Int,
        paymentMethod: String,
        tenor: Int,
        dependentsAmount: Int,
        context: Context
    ){
        _isLoading.value = true
        _isSuccessful.value = "pending"
        ApiConfig.getApiService().postRequestBorrwer("jwt=$token", loanAmount, reason, monthlyIncome, paymentMethod, tenor, dependentsAmount)
            .enqueue(object : Callback<PostBorrowerResponse>{
                override fun onResponse(
                    call: Call<PostBorrowerResponse>,
                    response: Response<PostBorrowerResponse>
                ) {
                    if(response.isSuccessful){
                        _isSuccessful.value = "success"
                        _isLoading.value = false
                        _postBorrowerResponse.postValue(response.body())
                    } else {
                        _isSuccessful.value = "failed"
                        _isLoading.value = false
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
                            _isSuccessful.value = "failed"
                            _isLoading.value = false
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }

                }

                override fun onFailure(call: Call<PostBorrowerResponse>, t: Throwable) {
                    _isSuccessful.value = "failed"
                    _isLoading.value = false
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

}