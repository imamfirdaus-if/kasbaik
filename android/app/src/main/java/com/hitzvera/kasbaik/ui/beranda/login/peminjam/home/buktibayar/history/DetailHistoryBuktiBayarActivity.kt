package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityDetailHistoryBuktiBayarBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.UploadBuktiBayarActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiActivity

class DetailHistoryBuktiBayarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBuktiBayarBinding
    private lateinit var linkImage: String
    private lateinit var message: String
    private lateinit var createdAt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBuktiBayarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        bindData()
        binding.btnOkay.setOnClickListener { finish() }
    }

    private fun getData(){
        linkImage = intent.getStringExtra(UploadBuktiBayarActivity.LINKBUKTI).toString()
        message = intent.getStringExtra(NotifikasiActivity.MESSAGE).toString()
        createdAt = intent.getStringExtra(NotifikasiActivity.DIBUAT).toString()
    }
    private fun bindData(){
        Glide.with(this).load(linkImage).into(binding.ivBuktiBayar)
        binding.tvMessage.text = message
        binding.tvCreatedAt.text = createdAt
    }
}