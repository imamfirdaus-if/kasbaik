package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.ProfileUserAdminResponse
import com.hitzvera.kasbaik.response.ProfilesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileUserAdminViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private lateinit var temp: List<ProfilesItem>

    private var _list = MutableLiveData<ProfilesItem>()
    val list: LiveData<ProfilesItem> = _list

    fun getProfileUser(token: String, idUser: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getProfileUserAdmin("jwt=$token", idUser)
            .enqueue(object: Callback<ProfileUserAdminResponse> {
                override fun onResponse(
                    call: Call<ProfileUserAdminResponse>,
                    response: Response<ProfileUserAdminResponse>
                ) {
                    if (response.isSuccessful){
                        temp = response.body()!!.profiles
                        _list.value = temp[0]
                        _isLoading.value = false
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ProfileUserAdminResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}