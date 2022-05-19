package com.hitzvera.kasbaik.ui.beranda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityBerandaBinding
import com.hitzvera.kasbaik.ui.beranda.daftar.RegisterActivity
import com.hitzvera.kasbaik.ui.beranda.isikas.IsiKasActivity
import com.hitzvera.kasbaik.ui.beranda.login.LoginActivity
import com.hitzvera.kasbaik.ui.beranda.tentang.AboutActivity

class BerandaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityBerandaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.about.setOnClickListener(this)
        binding.isikas.setOnClickListener(this)
        binding.register.setOnClickListener(this)
        binding.login.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.isikas -> {
                val intent = Intent(this, IsiKasActivity::class.java)
                startActivity(intent)
            }
            R.id.login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}