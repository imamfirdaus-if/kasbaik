package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.edit

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.response.EditBorrowerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusEditViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessful = MutableLiveData<String>()
    val isSuccessful: LiveData<String> = _isSuccessful

    fun editRequestBorrowing(
        token:String,
        idBorrower: String,
        loanAmount: Int,
        reasonBorrower: String,
        tenor: Int,
        monthlyIncome: Int,
        donasi: Int,
        dependentsAmount: Int,
        context: Context
    ) {
        _isLoading.value = true
        _isSuccessful.value = "pending"
        ApiConfig.getApiService().editRequestBorrowing("jwt=$token", idBorrower, loanAmount, reasonBorrower, dependentsAmount,tenor,monthlyIncome,donasi, )
            .enqueue(object: Callback<EditBorrowerResponse>{
                override fun onResponse(call: Call<EditBorrowerResponse>, response: Response<EditBorrowerResponse>) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _isSuccessful.value = "success"
                        Toast.makeText(context, "Berhasil mengedit pinjaman", Toast.LENGTH_LONG).show()
                        Log.e("CHECK", response.body()?.borrower.toString())
                    } else {
                        _isSuccessful.value = "failed"
                        Toast.makeText(context, "Gagal mengedit pinjaman", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<EditBorrowerResponse>, t: Throwable) {
                    _isLoading.value = true
                    _isSuccessful.value = "failed"
                    Toast.makeText(context, "Gagal mengedit pinjaman", Toast.LENGTH_LONG).show()
                }

            })
    }

}