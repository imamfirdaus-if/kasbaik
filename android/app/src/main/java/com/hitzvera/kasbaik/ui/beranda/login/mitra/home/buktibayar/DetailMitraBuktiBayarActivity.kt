package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.buktibayar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityDetailMitraBuktiBayarBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.UploadBuktiBayarActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiActivity

class DetailMitraBuktiBayarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMitraBuktiBayarBinding
    private lateinit var viewModel: DetailBuktiBayarViewModel
    private lateinit var idMessage: String
    private lateinit var message: String
    private lateinit var linkImage: String
    private lateinit var createdAt: String
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMitraBuktiBayarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(HomePeminjamActivity.TOKEN).toString()
        idMessage = intent.getStringExtra(HomePeminjamActivity.IDMESSAGE).toString()
        viewModel = ViewModelProvider(this)[DetailBuktiBayarViewModel::class.java]
        getData()
        setData()
        binding.btnCancel.setOnClickListener { finish() }
        binding.btnSave.setOnClickListener {
            val radioId: Int = binding.radioGroupSebagai.checkedRadioButtonId
            val radioButton: RadioButton = findViewById(radioId)
            val status: String = radioButton.text.toString()
            viewModel.updateBuktiBayar(token, idMessage, status, this)
            viewModel.isLoading.observe(this){
                showLoading(it)
            }
            finish()
        }
    }

    private fun getData(){
        message = intent.getStringExtra(NotifikasiActivity.MESSAGE).toString()
        linkImage = intent.getStringExtra(UploadBuktiBayarActivity.LINKBUKTI).toString()
        createdAt = intent.getStringExtra(NotifikasiActivity.DIBUAT).toString()
    }

    private fun setData(){
        Glide.with(this).load(linkImage).into(binding.ivBuktiBayar)
        binding.tvMessage.text = message
        binding.tvCreatedAt.text = createdAt
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarDetailBuktiBayar.visibility = View.VISIBLE
        } else {
            binding.progressBarDetailBuktiBayar.visibility = View.GONE
        }
    }
}