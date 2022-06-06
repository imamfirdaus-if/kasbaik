package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.profile
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.Profile
import com.hitzvera.kasbaik.response.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileViewModel: ViewModel() {
    private var _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessful = MutableLiveData<String>()
    val isSuccessful: LiveData<String> = _isSuccessful

    fun getProfile(context: Context, token: String){
        _isLoading.value = true
        ApiConfig.getApiService().getRequestProfile("jwt=$token")
            .enqueue(object: Callback<Profile>{
                override fun onResponse(
                    call: Call<Profile>,
                    response: Response<Profile>
                ) {
                    if(response.isSuccessful){
                        _profileResponse.postValue(response.body()?.profile)
                        _isLoading.value = false
                    }

                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    Toast.makeText(context, "Failed fetch the data", Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun postProfile(
        context: Context,
        token: String,
        file1: MultipartBody.Part,
        file2: MultipartBody.Part,
        file3: MultipartBody.Part,
        usia: RequestBody,
        gender: RequestBody,
        alamatTinggal: RequestBody,
        alamatKtp: RequestBody,
        profesi: RequestBody
    ){
        _isSuccessful.value = "pending"
        _isLoading.value = true
        ApiConfig.getApiService().postRequestProfile("jwt=$token", file1, file2, file3, usia, gender, alamatTinggal, alamatKtp, profesi)
            .enqueue(object : Callback<Profile>{
                override fun onResponse(
                    call: Call<Profile>,
                    response: Response<Profile>
                ) {
                    if(response.isSuccessful){
                        _isLoading.value = false
                        _isSuccessful.value = "success"
                        Toast.makeText(context, "Success update data", Toast.LENGTH_SHORT).show()
                    } else {
                        _isLoading.value = false
                        _isSuccessful.value = "failed"
                        Toast.makeText(context, "Failed update data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    _isLoading.value = false
                    _isSuccessful.value = "failed"
                    Toast.makeText(context, "Failed update data", Toast.LENGTH_SHORT).show()
                }

            })
    }
}