package com.hitzvera.kasbaik.ui.dashboard.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityDashboardDaftarBinding
import com.hitzvera.kasbaik.databinding.ActivityDashboardLoginBinding
import com.hitzvera.kasbaik.ui.beranda.daftar.RegisterActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.LoginAsPeminjamActivity
import com.hitzvera.kasbaik.ui.dashboard.daftar.DashboardDaftarActivity

class DashboardLoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDashboardLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.tvText7.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_login -> {
                Intent(this, LoginAsPeminjamActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.tv_text_7 -> {
                Intent(this, DashboardDaftarActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }
}