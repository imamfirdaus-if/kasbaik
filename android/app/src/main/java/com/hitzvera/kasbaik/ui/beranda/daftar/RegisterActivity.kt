package com.hitzvera.kasbaik.ui.beranda.daftar

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
import com.hitzvera.kasbaik.databinding.ActivityRegisterBinding
import com.hitzvera.kasbaik.ui.beranda.login.LoginActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.btnRegister.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_register -> {
                val username = binding.edUsername.text.trim().toString()
                val email = binding.edEmail.text.trim().toString()
                val phone = binding.edPhoneNumber.text.trim().toString()
                val password = binding.edPassword.text.trim().toString()
                val isMitra = binding.cbLoginAsMitra.isChecked
                lateinit var role: String;
                role = if(isMitra){
                    "mitra"
                } else {
                    "user"
                }
                if(validateForm(username, email, phone, password)) {
                    viewModel.createAccount(username, email, phone, password, role,this)
                    viewModel.isLoading.observe(this){
                        showLoading(it)
                    }
                    viewModel.isSuccessful.observe(this){
                        showCustomDialog(it)
                    }
                }
            }
        }
    }

    private fun validateForm(username: String, email: String, phone: String, password: String): Boolean {

        return if(username.isNotEmpty()
            && email.isNotEmpty()
            && phone.isNotEmpty()
            && password.isNotEmpty()
            && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            ) {
            true
        } else {
            Toast.makeText(this, "Data have to be valid", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun showCustomDialog(state: String){
        if(state == "success"){
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.register_pop_up_success)
            val btnLogin: Button = dialog.findViewById(R.id.btn_login)
            btnLogin.setOnClickListener {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
            dialog.show()
        } else if(state == "failed") {
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
            binding.progressBarRegister.visibility = View.VISIBLE
        } else {
            binding.progressBarRegister.visibility = View.GONE
        }
    }
}