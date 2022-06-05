package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.response.GetUpdateStatusResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel: ViewModel() {
    private var status = "done"

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _listBorrower = MutableLiveData<List<Borrower>>()
    val listBorrower: LiveData<List<Borrower>> = _listBorrower

    fun getListPinjaman(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getRiwayat("jwt=$token", status)
            .enqueue(object: Callback<List<Borrower>>{
                override fun onResponse(
                    call: Call<List<Borrower>>,
                    response: Response<List<Borrower>>
                ) {
                    if (response.isSuccessful){
                        _listBorrower.postValue(response.body())
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Borrower>>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })

    }

}