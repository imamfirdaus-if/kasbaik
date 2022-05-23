package com.hitzvera.kasbaik.ui.beranda.daftar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityRegisterBinding

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
                if(validateForm(username, email, phone, password)) {
                    viewModel.createAccount(username, email, phone, password, this)
                    viewModel.isLoading.observe(this){
                        showLoading(it)
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

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarRegister.visibility = View.VISIBLE
        } else {
            binding.progressBarRegister.visibility = View.GONE
        }
    }
}