package com.hitzvera.kasbaik.ui.dashboard.daftar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityDashboardDaftarBinding
import com.hitzvera.kasbaik.ui.beranda.daftar.RegisterActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.LoginAsPeminjamActivity
import com.hitzvera.kasbaik.ui.dashboard.login.DashboardLoginActivity

class DashboardDaftarActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDashboardDaftarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener(this)
        binding.tvText7.setOnClickListener(this)
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
}