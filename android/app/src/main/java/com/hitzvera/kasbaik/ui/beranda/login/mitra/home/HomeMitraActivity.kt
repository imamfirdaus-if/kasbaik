package com.hitzvera.kasbaik.ui.beranda.login.mitra.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHomeMitraBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.editprofile.MitraProfileActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.ListPeminjamActivity

class HomeMitraActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHomeMitraBinding
    private lateinit var token: String
    private lateinit var viewModel: HomeMitraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMitraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(TOKEN)!!
        binding.btnListPeminjam.setOnClickListener(this)
        binding.btnEditProfile.setOnClickListener(this)

    }

    companion object {
        const val TOKEN = "token"
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_list_peminjam -> {
                Intent(this, ListPeminjamActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    startActivity(it)
                }
            }
            R.id.btn_edit_profile -> {
                Intent(this, MitraProfileActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    startActivity(it)
                }
            }

        }
    }
}