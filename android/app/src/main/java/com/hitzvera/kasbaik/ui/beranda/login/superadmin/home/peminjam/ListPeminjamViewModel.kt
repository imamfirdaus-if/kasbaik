package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.peminjam

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.ListBorrowerPendingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListPeminjamViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _list = MutableLiveData<List<ListBorrowerPendingResponseItem>>()
    val list: LiveData<List<ListBorrowerPendingResponseItem>> = _list

    fun getBorrowerPending(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getListBorrowerPending("jwt=$token")
            .enqueue(object: Callback<List<ListBorrowerPendingResponseItem>> {
                override fun onResponse(
                    call: Call<List<ListBorrowerPendingResponseItem>>,
                    response: Response<List<ListBorrowerPendingResponseItem>>
                ) {
                    if (response.isSuccessful){
                        _list.postValue(response.body())
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ListBorrowerPendingResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}