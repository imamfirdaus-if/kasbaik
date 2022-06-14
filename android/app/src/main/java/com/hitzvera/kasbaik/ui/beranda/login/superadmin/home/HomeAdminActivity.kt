package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHomeAdminBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.LoginAsPeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.mitra.ListMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pembayaran.ListPaymentActivity
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.peminjam.ListPeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna.ListPenggunaActivity
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.profile.ProfileAdminActivity

class HomeAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeAdminBinding
    private lateinit var token: String
    private lateinit var name: String
    private lateinit var vm: HomeAdminViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)



        token = intent.getStringExtra(TOKEN).toString()
        name = intent.getStringExtra(NAME).toString()
        vm = ViewModelProvider(this)[HomeAdminViewModel::class.java]
        binding.apply {
            vm.getSummary(token, this@HomeAdminActivity)
            vm.list.observe(this@HomeAdminActivity){
                tvPending.text = it.pending.toString()
                tvPeminjamanBerhasil.text = it.accepted.toString()
                tvJumlahPeminjam.text = it.borrower.toString()
            }
            tvGreeting.text = "Selamat Datang, {name}!"
            btnProfile.setOnClickListener {
                Intent(this@HomeAdminActivity, ProfileAdminActivity::class.java).also {
                    it.putExtra("TOKEN", token)
                    it.putExtra("NAME", name)
                    startActivity(it)
                }
            }
            btnMitra.setOnClickListener {
                Intent(this@HomeAdminActivity, ListMitraActivity::class.java).also {
                    it.putExtra("TOKEN", token)
                    startActivity(it)
                }
            }
            btnPengguna.setOnClickListener {
                Intent(this@HomeAdminActivity, ListPenggunaActivity::class.java).also {
                    it.putExtra("TOKEN", token)
                    startActivity(it)
                }
            }
            btnListPeminjam.setOnClickListener {
                Intent(this@HomeAdminActivity, ListPeminjamActivity::class.java).also {
                    it.putExtra("TOKEN", token)
                    startActivity(it)
                }
            }
            btnPayment.setOnClickListener {
                Intent(this@HomeAdminActivity, ListPaymentActivity::class.java).also {
                    it.putExtra("TOKEN", token)
                    startActivity(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.logout_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                Intent(this, LoginAsPeminjamActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
                return true
            }
            else -> return true
        }
    }

    companion object{
        const val TOKEN = "token"
        const val NAME = "name"
    }
}