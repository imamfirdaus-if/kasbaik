package com.hitzvera.kasbaik.ui.beranda.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityLoginBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.LoginAsMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.LoginAsPeminjamActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginAsMitra.setOnClickListener(this)
        binding.loginAsPeminjam.setOnClickListener(this)


    }

    override fun onClick(view: View) {
       when(view.id){
           R.id.login_as_peminjam -> {
               val intent = Intent(this, LoginAsPeminjamActivity::class.java)
               startActivity(intent)
           }
           R.id.login_as_mitra -> {
               val intent = Intent(this, LoginAsMitraActivity::class.java)
               startActivity(intent)
           }
       }
    }
}