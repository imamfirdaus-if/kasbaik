package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityNotifikasiBinding
import com.hitzvera.kasbaik.databinding.ActivityNotifikasiDetailBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiActivity

class NotifikasiDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotifikasiDetailBinding
    private lateinit var viewModel: NotifikasiDetailViewModel
    private lateinit var token: String
    private lateinit var idMessage: String
    private lateinit var message: String
    private lateinit var createdAt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifikasiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[NotifikasiDetailViewModel::class.java]
        token = intent.getStringExtra(NotifikasiActivity.TOKEN).toString()
        message = intent.getStringExtra(NotifikasiActivity.MESSAGE).toString()
        createdAt = intent.getStringExtra(NotifikasiActivity.DIBUAT).toString()
        idMessage = intent.getStringExtra(NotifikasiActivity.IDMESSAGE).toString()

        binding.tvCreatedAt.text = createdAt
        binding.tvMessage.text = message
        viewModel.updateHasRead(token, idMessage, this)
        binding.btnOkay.setOnClickListener {
            finish()
        }
    }
}