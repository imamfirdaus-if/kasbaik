package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.peminjam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityListPeminjam2Binding
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.peminjam.detail.DetailPinjamanAdminActivity

class ListPeminjamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListPeminjam2Binding
    private lateinit var token: String
    private lateinit var vm: ListPeminjamViewModel
    private val adapter: ListPeminjamAdapter by lazy {
        ListPeminjamAdapter(ListPeminjamAdapter.OnClickListener{ item ->
            Intent(this, DetailPinjamanAdminActivity::class.java).also {
                it.putExtra("TOKEN", token)
                it.putExtra("IDUSER", item.idBorrower)
                it.putExtra("NAME", item.namaLengkap)
                startActivity(it)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPeminjam2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra("TOKEN").toString()
        binding.apply {
            rvListPeminjam.setHasFixedSize(true)
            rvListPeminjam.layoutManager = LinearLayoutManager(this@ListPeminjamActivity)
            rvListPeminjam.adapter = adapter
            vm = ViewModelProvider(this@ListPeminjamActivity)[ListPeminjamViewModel::class.java]
            vm.apply {
                vm.getBorrowerPending(token, this@ListPeminjamActivity)
                vm.list.observe(this@ListPeminjamActivity){
                    if (it != null){
                        adapter.setData(it)
                    }
                    isLoading.observe(this@ListPeminjamActivity){
                        showLoading(it)
                    }
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarListPeminjam.visibility = View.VISIBLE
        } else {
            binding.progressBarListPeminjam.visibility = View.GONE
        }
    }
}