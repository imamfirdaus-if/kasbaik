package com.hitzvera.kasbaik.ui.beranda.login.mitra

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityLoginAsMitraBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity

class LoginAsMitraActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginAsMitraBinding
    private lateinit var viewModel: LoginAsMitraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAsMitraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginAsMitraViewModel::class.java]
        binding.btnLoginMitra.setOnClickListener(this)
    }

    private fun validateLogin(email: String, password: String): Boolean{
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            && password.isNotEmpty() && email.isNotEmpty()) {
            return true
        }
        return false
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_login_mitra -> {
                val email = binding.edEmailLoginMitra.text.trim().toString()
                val password = binding.edPasswordLoginMitra.text.trim().toString()
                login(email, password)
                viewModel.isSuccessful.observe(this){
                    showCustomDialog(it)
                }
                viewModel.isLoading.observe(this){
                    showLoading(it)
                }
            }
        }
    }

    private fun login(email: String, password: String){
        if(validateLogin(email, password)) {
            viewModel.loginMitra(email, password, this)
        } else {
            Toast.makeText(this, applicationContext.getString(R.string.data_have_to_be_valid), Toast.LENGTH_SHORT).show()
        }

    }

    private fun showCustomDialog(state: String){
        if(state == "success"){
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.register_pop_up_success)
            val btnLogin: Button = dialog.findViewById(R.id.btn_login)
            val tvTitle: TextView = dialog.findViewById(R.id.tv_successful)
            tvTitle.text = "Login Berhasil"
            btnLogin.visibility = View.GONE
            dialog.setOnDismissListener {
                viewModel.token.observe(this){
                    if (it!=null){
                        Intent(this, HomeMitraActivity::class.java).also { intent ->
                            intent.putExtra(HomeMitraActivity.TOKEN, it.token)
                            startActivity(intent)
                        }
                    }
                }
            }
            dialog.show()
        } else if(state == "failed"){
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.register_pop_up_failed)
            viewModel.errorMessage.observe(this){
                val tvErrorMessage: TextView = dialog.findViewById(R.id.tv_error_message)
                tvErrorMessage.text = it
            }
            val retryBtn: Button = dialog.findViewById(R.id.btn_retry)
            retryBtn.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarMitra.visibility = View.VISIBLE
        } else {
            binding.progressBarMitra.visibility = View.GONE
        }
    }

}