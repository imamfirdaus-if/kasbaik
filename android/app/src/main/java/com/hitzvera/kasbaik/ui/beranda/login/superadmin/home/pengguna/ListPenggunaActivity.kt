package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityListPenggunaBinding

class ListPenggunaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListPenggunaBinding
    private lateinit var token: String
    private lateinit var vm: ListPenggunaViewModel
    private lateinit var adapter: ListPenggunaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPenggunaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra("TOKEN").toString()
        binding.apply {
            rvListPengguna.layoutManager = LinearLayoutManager(this@ListPenggunaActivity)
            rvListPengguna.adapter = adapter
            vm = ViewModelProvider(this@ListPenggunaActivity)[ListPenggunaViewModel::class.java]
            vm.apply {
                getListPengguna(token, this@ListPenggunaActivity)
                list.observe(this@ListPenggunaActivity){
                    if (it!=null){
                        adapter.setData(it)
                    }
                }
                isLoading.observe(this@ListPenggunaActivity){
                    showLoading(it)
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarListPengguna.visibility = View.VISIBLE
        } else {
            binding.progressBarListPengguna.visibility = View.GONE
        }
    }
}