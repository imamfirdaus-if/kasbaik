package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHomePeminjamBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.pinjamdana.PinjamDanaActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.profile.ProfileActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.zakat.ZakatActivity
import com.hitzvera.kasbaik.ui.beranda.tentang.AboutActivity

class HomePeminjamActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: HomePeminjamViewModel
    private lateinit var binding: ActivityHomePeminjamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePeminjamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(TOKEN)
        viewModel = ViewModelProvider(this)[HomePeminjamViewModel::class.java]

        profileResponse(token!!)
        imageSlider()
        clickListener()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_about -> {
                Intent(this, AboutActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.btn_zakat -> {
                Intent(this, ZakatActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.btn_profile -> {
               goToProfile()
            }
            R.id.btn_pinjam_dana -> {
                val token = intent.getStringExtra(TOKEN)
                viewModel.reqProfilePeminjam(this, token!!)
                viewModel.profileResponse.observe(this){
                    if(it!=null && it.alamatTinggal.isNullOrBlank()){
                        val dialog = Dialog(this)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.pop_up_command)
                        val btnLengkapi: Button = dialog.findViewById(R.id.btn_isi)
                        val tvDeskripsi: TextView = dialog.findViewById(R.id.text_description)
                        tvDeskripsi.text = "Profile Anda Belum lengkap Silahkan lengkapi"
                        btnLengkapi.text = "Lengkapi profile"
                        btnLengkapi.setOnClickListener {
                            goToProfile()
                            dialog.dismiss()
                        }
                        dialog.show()
                    } else {
                        Intent(this, PinjamDanaActivity::class.java).also { intent ->
                            intent.putExtra(TOKEN, token)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun goToProfile(){
        Intent(this, ProfileActivity::class.java).also {
            it.putExtra(TOKEN, intent.getStringExtra(TOKEN))
            Log.e("TOKEN", intent.getStringExtra(TOKEN).toString())
            startActivity(it)
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarHome.visibility = View.VISIBLE
        } else {
            binding.progressBarHome.visibility = View.GONE
        }
    }

    private fun imageSlider(){
        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel(R.drawable.image_bantu_usaha, "Bantu Usaha"))
        imageList.add(SlideModel(R.drawable.image_berdayakan_sesama, "Berdayakan sesama"))
        imageList.add(SlideModel(R.drawable.image_bunga_0, "Dapatkan pinjaman dengan bunga 0%"))

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }
    private fun profileResponse(token: String){
        viewModel.reqProfilePeminjam(this, token)
        viewModel.profileResponse.observe(this){
            if(it!=null){
                binding.welcomeTitle.text = getString(R.string.greet_user, it.namaLengkap)
            }
        }
        viewModel.isLoading.observe(this){
            if(it!=null){
                showLoading(it)
            }
        }
    }
    private fun clickListener(){
        binding.btnAbout.setOnClickListener(this)
        binding.btnZakat.setOnClickListener(this)
        binding.btnProfile.setOnClickListener(this)
        binding.btnPinjamDana.setOnClickListener(this)
    }
    companion object {
        const val TOKEN = "token"
    }


}