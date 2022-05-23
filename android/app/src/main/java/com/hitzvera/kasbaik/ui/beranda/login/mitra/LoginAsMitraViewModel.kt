package com.hitzvera.kasbaik.ui.beranda.login.mitra

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAsMitraViewModel {
    private var _isLoading = MutableLiveData<String>()
    val isLoading: LiveData<String> = _isLoading


}