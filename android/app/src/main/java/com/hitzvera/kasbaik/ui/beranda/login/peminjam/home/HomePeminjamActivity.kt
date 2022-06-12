package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.LoginAsPeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.UploadBuktiBayarActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.history.HistoryBuktiBayarActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.pinjamdana.PinjamDanaActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.profile.ProfileActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat.HistoryActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.StatusActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.zakat.ZakatActivity
import com.hitzvera.kasbaik.ui.beranda.tentang.AboutActivity
import com.hitzvera.kasbaik.ui.dashboard.daftar.DashboardDaftarActivity

class HomePeminjamActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: HomePeminjamViewModel
    private lateinit var binding: ActivityHomePeminjamBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var token: String
    private var gender: String? = null
    private var usia: Int? = null
    private var pekerjaan: String? = null
    private var hasStopped = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePeminjamBinding.inflate(layoutInflater)
        setContentView(binding.root)


        preferences = getSharedPreferences(DashboardDaftarActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        token = intent.getStringExtra(TOKEN).toString()
        gender = intent.getStringExtra(GENDER)
        usia = intent.getIntExtra(USIA, 0)
        pekerjaan = intent.getStringExtra(PEKERJAAN)
        viewModel = ViewModelProvider(this)[HomePeminjamViewModel::class.java]
        hasStopped = 0
        profileResponse(token)
        imageSlider()
        clickListener()
    }


    override fun onResume() {
        super.onResume()
        if(hasStopped == 1){
            recreate()
        }
    }

    override fun onStop() {
        super.onStop()
        hasStopped = 1
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.logout_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                preferences.edit().apply {
                    clear()
                    apply()
                }
                Intent(this, LoginAsPeminjamActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
                return true
            }
            else -> return true
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_about -> {
                Intent(this, AboutActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.btn_profile -> {
               goToProfile()
            }
            R.id.btn_pinjam_dana -> {
                viewModel.profileResponse.observe(this){
                    if(it!=null && it.alamatTinggal.isNullOrBlank()){
                        val dialog = Dialog(this)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(true)
                        dialog.setContentView(R.layout.pop_up_command)
                        val btnLengkapi: Button = dialog.findViewById(R.id.btn_isi)
                        val tvDeskripsi: TextView = dialog.findViewById(R.id.text_description)
                        tvDeskripsi.text = "Profile Anda Belum lengkap Silahkan lengkapi"
                        btnLengkapi.text = "Lengkapi profile"
                        if(!dialog.isShowing){
                            dialog.show()
                        }
                        btnLengkapi.setOnClickListener {
                            dialog.dismiss()
                            Log.e("button", "button working")
                        }
                        dialog.setOnDismissListener {
                            goToProfile()
                            Log.e("dismis", "it's been dismissed")
                        }
                    } else {
                        Intent(this, PinjamDanaActivity::class.java).also { intent ->
                            intent.putExtra(TOKEN, token)
                            intent.putExtra(USIA, usia)
                            intent.putExtra(GENDER, gender)
                            intent.putExtra(PEKERJAAN,pekerjaan)
                            startActivity(intent)
                        }
                    }
                }
            }
            R.id.btn_status -> {
                Intent(this, StatusActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    startActivity(it)
                }
            }
            R.id.btn_riwayat -> {
                Intent(this, HistoryActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    startActivity(it)
                }
            }
            R.id.btn_notifikasi -> {
                Intent(this, NotifikasiActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    startActivity(it)
                }
            }
            R.id.btn_bukti_bayar -> {
                Intent(this, UploadBuktiBayarActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    startActivity(it)
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
                if(!it.alamatTinggal.isNullOrBlank()){

                }
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
        binding.btnBuktiBayar.setOnClickListener(this)
        binding.btnProfile.setOnClickListener(this)
        binding.btnPinjamDana.setOnClickListener(this)
        binding.btnStatus.setOnClickListener(this)
        binding.btnRiwayat.setOnClickListener(this)
        binding.btnNotifikasi.setOnClickListener(this)
    }
    companion object {
        const val TOKEN = "token"
        const val USIA = "usia"
        const val PEKERJAAN = "pekerjaan"
        const val GENDER = "gender"
        const val IDMESSAGE = "idmessage"
    }


}