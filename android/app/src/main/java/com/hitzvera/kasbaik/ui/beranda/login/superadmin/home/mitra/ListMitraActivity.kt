package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.mitra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityListMitraBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.ListAdapter

class ListMitraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListMitraBinding
    private lateinit var token: String
    private lateinit var vm: ListMitraViewModel
    private val Adapter: ListMitraAdapter by lazy {
        ListMitraAdapter(ListMitraAdapter.OnClickListener{ item ->
            //Do Nothing
        }, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMitraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra("TOKEN").toString()
        vm = ViewModelProvider(this)[ListMitraViewModel::class.java]
        binding.apply {
            vm.apply {
                getListMitra(token, this@ListMitraActivity)
                list.observe(this@ListMitraActivity){
                    if (it!=null){
                        Adapter.setData(it)
                    }
                }
                isLoading.observe(this@ListMitraActivity){
                    showLoading(it)
                }
            }
            rvListMitra.setHasFixedSize(true)
            rvListMitra.layoutManager = LinearLayoutManager(this@ListMitraActivity)
            rvListMitra.adapter = Adapter
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarListMitra.visibility = View.VISIBLE
        } else {
            binding.progressBarListMitra.visibility = View.GONE
        }
    }
}