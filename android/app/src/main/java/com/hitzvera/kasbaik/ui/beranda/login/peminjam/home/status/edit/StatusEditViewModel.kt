package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.edit

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.Borrower
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
        context: Context
    ) {
        _isLoading.value = true
        _isSuccessful.value = "pending"
        ApiConfig.getApiService().editRequestBorrowing("jwt=$token", idBorrower, loanAmount, reasonBorrower, tenor, monthlyIncome, donasi)
            .enqueue(object: Callback<List<Int>>{
                override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _isSuccessful.value = "success"
                        Toast.makeText(context, "Berhasil mengedit pinjaman", Toast.LENGTH_LONG).show()
                    }
                    _isSuccessful.value = "failed"
                    Toast.makeText(context, "Gagal mengedit pinjaman", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                    _isLoading.value = true
                    _isSuccessful.value = "failed"
                    Toast.makeText(context, "Gagal mengedit pinjaman", Toast.LENGTH_LONG).show()
                }

            })
    }

}