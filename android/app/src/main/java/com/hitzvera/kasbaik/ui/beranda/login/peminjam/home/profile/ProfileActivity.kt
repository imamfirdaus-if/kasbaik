package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityProfileBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity

class ProfileActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = intent.getStringExtra(HomePeminjamActivity.TOKEN)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.getProfile(this, token!!)
        viewModel.profileResponse.observe(this){
            if(it!=null){
                binding.tvName.text = it.namaLengkap
                binding.tvWhatsapp.text = it.noWa
                binding.tvAlamat.text = it.alamatTinggal.toString()
                if(it.alamatTinggal.isNullOrBlank()){
                    Log.e("THIS", "MASUK GA")
                }
            }
        }
    }

    override fun onClick(view: View) {
        when(view.id){

        }
    }
}