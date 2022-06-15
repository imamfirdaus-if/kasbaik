package com.hitzvera.kasbaik.ui.dashboard.daftar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityDashboardDaftarBinding
import com.hitzvera.kasbaik.ui.beranda.daftar.RegisterActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.LoginAsPeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.HomeAdminActivity
import com.hitzvera.kasbaik.ui.dashboard.login.DashboardLoginActivity

class DashboardDaftarActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDashboardDaftarBinding
    private var isRemembered = false
    private var token = ""
    private var role = ""
    private var username = ""
    private var idMitra = ""
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean(CHECKBOX, false)
        token = sharedPreferences.getString(LoginAsPeminjamActivity.TOKEN, "").toString()
        role = sharedPreferences.getString(LoginAsPeminjamActivity.ROLE, "").toString()
        username = sharedPreferences.getString(LoginAsPeminjamActivity.USERNAME, "").toString()
        idMitra = sharedPreferences.getString(LoginAsPeminjamActivity.ID_MITRA, "").toString()
        hasLogin(isRemembered, role, token, username)

        binding.btnRegister.setOnClickListener(this)
        binding.tvText7.text = getString(R.string.if_login)
        binding.tvText7.setOnClickListener(this)
    }

    private fun hasLogin(hasLogin: Boolean, role: String, token: String, username: String){
        if(hasLogin){
            if(role == "user"){
                Intent(this, HomePeminjamActivity::class.java).also { intent ->
                    intent.putExtra(HomePeminjamActivity.TOKEN, token)
                    startActivity(intent)
                    finish()
                }
            } else if(role == "admin") {
                Intent(this, HomeAdminActivity::class.java).also { intent ->
                    intent.putExtra(HomeAdminActivity.TOKEN, token)
                    intent.putExtra(HomeAdminActivity.NAME, username)
                    startActivity(intent)
                    finish()
                }
            }
            else if(role == "mitra"){
                Intent(this, HomeMitraActivity::class.java).also { intent ->
                    intent.putExtra(HomeMitraActivity.TOKEN, token)
                    intent.putExtra(HomeMitraActivity.NAME, username)
                    intent.putExtra(HomeMitraActivity.ID_MITRA, idMitra)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_register -> {
                Intent(this, RegisterActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.tv_text_7 -> {
                Intent(this, DashboardLoginActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }
    companion object {
        const val SHARED_PREFERENCES = "shared_preferance"
        const val CHECKBOX = "checkbox"
    }
}