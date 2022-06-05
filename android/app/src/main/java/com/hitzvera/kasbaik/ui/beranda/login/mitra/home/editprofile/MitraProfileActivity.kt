package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.editprofile

import android.content.Intent
import android.icu.lang.UCharacter.IndicPositionalCategory.NA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityMitraProfileBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity.Companion.TOKEN

class MitraProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: MitraProfileViewModel
    private lateinit var token: String
    private lateinit var binding: ActivityMitraProfileBinding
    private var photo: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMitraProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(HomeMitraActivity.TOKEN).toString()
        viewModel = ViewModelProvider(this)[MitraProfileViewModel::class.java]
        getData()
        binding.btnEdit.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    fun getData(){
        viewModel.getMitraProfile(token, this)
        viewModel.mitraProfileResponse.observe(this){
            if(it!=null){
                binding.apply {
                    tvName.text = it.partnerName
                    tvNomor.text = it.phone
                    tvLocation.text = it.locationMitra ?: ""
                    Glide.with(this@MitraProfileActivity)
                        .load(it.fotoProfile)
                        .into(ivAvatar)
                    photo = it.fotoProfile

                }
            }
        }
    }
    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_edit -> {
                Intent(this, EditProfileActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    it.putExtra(NAMA, binding.tvName.text.toString())
                    it.putExtra(NOMOR, binding.tvNomor.text.toString())
                    it.putExtra(LOCATION, binding.tvLocation.text.toString())
                    it.putExtra(PHOTO, photo)
                    startActivity(it)
                    finish()
                }
            }
            R.id.btn_cancel -> {
                finish()
            }
        }
    }

    companion object {
        const val TOKEN = "token"
        const val NAMA = "nama"
        const val NOMOR = "nomor"
        const val LOCATION = "location"
        const val PHOTO = "photo"
    }
}