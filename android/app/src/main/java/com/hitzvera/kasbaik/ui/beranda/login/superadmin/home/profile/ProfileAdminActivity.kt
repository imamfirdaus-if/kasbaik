package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityProfileAdminBinding

class ProfileAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileAdminBinding
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra("NAME").toString()
        binding.apply {
            tvNama.setText(name)
            btnSave.visibility = View.GONE
            btnKembali.setOnClickListener {
                finish()
            }
        }

    }
}