package com.hitzvera.kasbaik.ui.beranda.login.mitra.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHomeMitraBinding

class HomeMitraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeMitraBinding
    private lateinit var viewModel: HomeMitraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMitraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(TOKEN)
        binding.greetMitra.text = token

    }
    companion object {
        const val TOKEN = "token"
    }
}