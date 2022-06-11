package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.ListUserAdminResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPenggunaViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _list = MutableLiveData<List<ListUserAdminResponseItem>>()
    val list: LiveData<List<ListUserAdminResponseItem>> = _list

    fun getListPengguna(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getListPengguna("jwt=$token")
            .enqueue(object: Callback<List<ListUserAdminResponseItem>> {
                override fun onResponse(
                    call: Call<List<ListUserAdminResponseItem>>,
                    response: Response<List<ListUserAdminResponseItem>>
                ) {
                    if (response.isSuccessful){
                        _list.postValue(response.body())
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ListUserAdminResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}