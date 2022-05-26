package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.profile

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityProfileBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity
fun Boolean.toInt() = if (this) 1 else 0
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
                binding.tvName.setText(it.namaLengkap)
                binding.tvWhatsapp.setText(it.phone)
                if(it.alamatTinggal.isNullOrBlank()){
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
        binding.btnSave.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_save -> {
                val name = binding.tvName.text.trim().toString()
                val phone = binding.tvWhatsapp.text.trim().toString()
                val usia = binding.edUsia.text.trim()
                val gender = binding.radioPria.isChecked.toInt()
                val pekerjaan = binding.edPekerjaan.text
                val alamat = binding.edAlamatTinggal.text.toString()
                Log.e("TEST INPUT", "$name $phone $usia $gender $pekerjaan $alamat")
            }
        }
    }
}