package com.hitzvera.kasbaik.ui.beranda.login.mitra.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHomeMitraBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.editprofile.MitraProfileActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.ListPeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.PaymentActivity

class HomeMitraActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHomeMitraBinding
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMitraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(TOKEN)!!
        val name = intent.getStringExtra(NAME)
        binding.tvGreeting.text = getString(R.string.greet_user, name)
        binding.btnListPeminjam.setOnClickListener(this)
        binding.btnEditProfile.setOnClickListener(this)
        binding.btnPayment.setOnClickListener(this)

    }

    companion object {
        const val TOKEN = "token"
        const val NAME = "name"
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
            R.id.btn_payment -> {
                Intent(this, PaymentActivity::class.java).also {
                    it.putExtra(PaymentActivity.TOKEN, token)
                    startActivity(it)
                }
            }

        }
    }
}