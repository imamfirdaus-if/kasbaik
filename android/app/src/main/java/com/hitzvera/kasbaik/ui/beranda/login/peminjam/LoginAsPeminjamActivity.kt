package com.hitzvera.kasbaik.ui.beranda.login.peminjam

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityLoginAsPeminjamBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity

class LoginAsPeminjamActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginAsPeminjamBinding
    private lateinit var viewModel: LoginAsPeminjamViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAsPeminjamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginAsPeminjamViewModel::class.java]
        binding.btnLoginPeminjam.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_login_peminjam -> {
                val email = binding.edEmailLoginPeminjam.text.trim().toString()
                val password = binding.edPasswordLoginPeminjam.text.trim().toString()
                login(email, password)
                viewModel.token.observe(this){
                    if (it!=null){
                        Intent(this, HomePeminjamActivity::class.java).also { intent ->
                            intent.putExtra(HomePeminjamActivity.TOKEN, it.token)
                            startActivity(intent)
                        }
                    }
                }
                viewModel.isLoading.observe(this){
                    showLoading(it)
                }
            }
        }
    }

    private fun validateLogin(email: String, password: String): Boolean{
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            && password.length >= 6 && password.isNotEmpty() && email.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun login(email: String, password: String){
        if(validateLogin(email, password)) {
            viewModel.loginUser(email, password, this)
        } else {
            Toast.makeText(this, applicationContext.getString(R.string.data_have_to_be_valid), Toast.LENGTH_SHORT).show()
        }

    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarLoginPeminjam.visibility = View.VISIBLE
        } else {
            binding.progressBarLoginPeminjam.visibility = View.GONE
        }
    }

    companion object {
        const val TOKEN = "token"
    }
}