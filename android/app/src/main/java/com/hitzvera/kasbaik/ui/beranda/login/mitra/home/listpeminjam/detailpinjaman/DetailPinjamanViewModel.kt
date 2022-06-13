package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.detailpinjaman

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.CreditKatResponse
import com.hitzvera.kasbaik.response.CrediteApprovalResponse
import com.hitzvera.kasbaik.response.Data
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

    private var _creditScore = MutableLiveData<Int>()
    val creditScore: LiveData<Int> = _creditScore

    private var _creditScoreData = MutableLiveData<Data>()
    val creditScoreData: LiveData<Data> = _creditScoreData

    fun updateStatusRequest(token: String, status: String, message: String, idBorrower: String){
        _isLoading.value = true
        _isSuccessful.value = "pending"
        ApiConfig.getApiService().postRequestUpdateStatus("jwt=$token", status, message ,idBorrower)
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
                        var mErrorMessage = ""
                        try {
                            val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                            mErrorMessage = jsonObj.getString("message")
                        } catch (e: Throwable){
                            mErrorMessage = e.toString()
                        }

                        _errorMessage.value = mErrorMessage
                    }
                }

                override fun onFailure(call: Call<PostUpdataStatusResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isSuccessful.value = "failed"
                }

            })
    }

    fun getCreditData(token: String, idBorrower: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getCreditData("jwt=$token", idBorrower)
            .enqueue(object: Callback<CreditKatResponse>{
                override fun onResponse(
                    call: Call<CreditKatResponse>,
                    response: Response<CreditKatResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _creditScoreData.postValue(response.body()?.data)
                    } else {
                        Toast.makeText(context, "Gagal mendapatkan data credit", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CreditKatResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Gagal mendapatkan data credit", Toast.LENGTH_LONG).show()
                }

            })
    }

    fun getCreditScore(
        usiakat: Int,
        econkat: Int,
        pekerjaankat: Int,
        pinjamankekat: Int,
        telatharikat: Int,
        donasikat: Int,
        context: Context
    ){
        _isLoading.value = true
        ApiConfig.getApiService2().getCreditScore(usiakat, econkat, pekerjaankat, pinjamankekat,telatharikat, donasikat)
            .enqueue(object: Callback<CrediteApprovalResponse>{
                override fun onResponse(
                    call: Call<CrediteApprovalResponse>,
                    response: Response<CrediteApprovalResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        Log.i("CHECK", response.body().toString())
                        _creditScore.postValue(response.body()?.prediction)
                    } else {
                        Toast.makeText(context, "Gagal mendapat data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CrediteApprovalResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Gagal mendapat data", Toast.LENGTH_LONG).show()
                }

            })
    }

}