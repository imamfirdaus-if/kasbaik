package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pembayaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityListPaymentBinding

class ListPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListPaymentBinding
    private lateinit var token: String
    private lateinit var vm: ListPaymentViewModel
    private val adapter: ListPaymentAdapter by lazy {
        ListPaymentAdapter(ListPaymentAdapter.OnClickListener{item ->
            //Do Nothing
        }, token, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra("TOKEN").toString()
        binding.apply {
            rvListPayment.setHasFixedSize(true)
            rvListPayment.layoutManager = LinearLayoutManager(this@ListPaymentActivity)
            rvListPayment.adapter = adapter
            vm = ViewModelProvider(this@ListPaymentActivity)[ListPaymentViewModel::class.java]
            vm.getListPayment(token, this@ListPaymentActivity)
            vm.apply {
                list.observe(this@ListPaymentActivity){ item ->
                    if (item != null){
                        adapter.setData(item)
                    }
                    isLoading.observe(this@ListPaymentActivity){
                        showLoading(it)
                    }
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarListPayment.visibility = View.VISIBLE
        } else {
            binding.progressBarListPayment.visibility = View.GONE
        }
    }
}