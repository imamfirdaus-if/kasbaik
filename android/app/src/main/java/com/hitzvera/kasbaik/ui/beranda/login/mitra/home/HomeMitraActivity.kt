package com.hitzvera.kasbaik.ui.beranda.login.mitra.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHomeMitraBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.buktibayar.BuktiBayarActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.editprofile.MitraProfileActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.ListPeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.PaymentActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.LoginAsPeminjamActivity
import com.hitzvera.kasbaik.ui.dashboard.daftar.DashboardDaftarActivity

class HomeMitraActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHomeMitraBinding
    private lateinit var token: String
    private lateinit var preferences: SharedPreferences
    private lateinit var viewModel: HomeMitraViewModel
    private var idMitra = "6b7acb6c-9826-4dd6-84d5-90823220cb1a" // sementara
    private var hasStopped = false


    override fun onStop() {
        super.onStop()
        hasStopped = true
    }

    override fun onResume() {
        super.onResume()
        if(hasStopped){
            recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMitraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hasStopped = false
        token = intent.getStringExtra(TOKEN)!!
        val name = intent.getStringExtra(NAME)
        preferences = getSharedPreferences(DashboardDaftarActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this)[HomeMitraViewModel::class.java]
        viewModel.getMitraSummary(token, idMitra, this)
        viewModel.summaryMitraResponse.observe(this){
            if(it!=null){
                binding.tvJumlahPeminjam.text = it.borrower.toString()
                binding.tvPeminjamanDiterima.text = it.accepted.toString()
                binding.tvTotalPayment.text = it.totalPayment.toString()
                binding.tvTotalPending.text = it.pending.toString()
            }
        }


        binding.tvGreeting.text = getString(R.string.greet_user, name)
        binding.btnListPeminjam.setOnClickListener(this)
        binding.btnEditProfile.setOnClickListener(this)
        binding.btnPayment.setOnClickListener(this)
        binding.btnListBukti.setOnClickListener(this)

    }

    companion object {
        const val TOKEN = "token"
        const val NAME = "name"
        const val LINKIMAGE = "linkimage"
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
                    it.putExtra(TOKEN, token)
                    startActivity(it)
                }
            }
            R.id.btn_list_bukti -> {
                Intent(this, BuktiBayarActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    startActivity(it)
                }
            }

        }
    }
}