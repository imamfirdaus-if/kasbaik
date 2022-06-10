package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.mitra

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.GetListMitraResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMitraViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _list = MutableLiveData<List<GetListMitraResponseItem>>()
    val list: LiveData<List<GetListMitraResponseItem>> = _list

    fun getListMitra(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getListMitra("jwt=$token")
            .enqueue(object: Callback<List<GetListMitraResponseItem>>{
                override fun onResponse(
                    call: Call<List<GetListMitraResponseItem>>,
                    response: Response<List<GetListMitraResponseItem>>
                ) {
                    if (response.isSuccessful){
                        _list.postValue(response.body())
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<GetListMitraResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}