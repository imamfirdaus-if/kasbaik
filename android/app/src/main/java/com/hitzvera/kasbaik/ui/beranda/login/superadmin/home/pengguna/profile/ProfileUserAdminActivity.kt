package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna.profile

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityProfileUserAdminBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity


class ProfileUserAdminActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileUserAdminBinding
    private lateinit var viewModel: ProfileUserAdminViewModel
    private var linkImage1: String? = null
    private var linkImage2: String? = null
    private var linkImage3: String? = null

    override fun onResume() {
        super.onResume()
        val pekerjaan = resources.getStringArray(R.array.pekerjaan)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, pekerjaan)
        binding.pekerjaanItemContainer.setAdapter(arrayAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val token = intent.getStringExtra("TOKEN").toString()
        val id = intent.getStringExtra("IDUSER").toString()
        viewModel = ViewModelProvider(this)[ProfileUserAdminViewModel::class.java]
        viewModel.getProfileUser(token, id, this)
        viewModel.list.observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.setText(it.namaLengkap)
                    tvWhatsapp.setText(it.phone)
                    Glide.with(this@ProfileUserAdminActivity)
                        .load(it.fotoDiri)
                        .fallback(R.drawable.avatar_placeholder)
                        .error(R.drawable.avatar_placeholder)
                        .placeholder(R.drawable.avatar_placeholder)
                        .into(ivAvatar)
                    edAlamatTinggal.setText(it.alamatTinggal ?: "")
                    edAlamatKtp.setText(it.alamatKtp ?: "")
                    if (it.usia != null) {
                        edUsia.setText("${it.usia}")
                    }
                    if (it.fotoDiri.isNotBlank()) {
                        linkImage1 = it.fotoDiri
                        linkImage2 = it.fotoKtp
                        linkImage3 = it.fotoSelfie
                    }
                }
                if (it.alamatTinggal.isNullOrBlank()) {
                    val dialog = Dialog(this)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(true)
                    dialog.setContentView(R.layout.pop_up_command)
                    val btnIsi: Button = dialog.findViewById(R.id.btn_isi)
                    btnIsi.setOnClickListener { dialog.dismiss() }
                    dialog.show()
                }
            }
        }
        binding.btnChoose1.setOnClickListener(this)
        binding.btnChoose2.setOnClickListener(this)
        binding.btnChoose3.setOnClickListener(this)
        binding.seeImage1.setOnClickListener(this)
        binding.seeImage2.setOnClickListener(this)
        binding.seeImage3.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.see_image_1 -> {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.see_image)
                val ivImage: ImageView = dialog.findViewById(R.id.image)
                Glide.with(this).load(linkImage1).into(ivImage)
                dialog.show()
            }
            R.id.see_image_2 -> {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.see_image)
                val ivImage: ImageView = dialog.findViewById(R.id.image)
                Glide.with(this).load(linkImage2).into(ivImage)
                dialog.show()
            }
            R.id.see_image_3 -> {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.see_image)
                val ivImage: ImageView = dialog.findViewById(R.id.image)
                Glide.with(this).load(linkImage3).into(ivImage)
                dialog.show()
            }
            R.id.btn_cancel -> {
                finish()
            }
        }
    }
    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarProfile.visibility = View.VISIBLE
        } else {
            binding.progressBarProfile.visibility = View.GONE
        }
    }
}