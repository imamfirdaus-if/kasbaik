package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.peminjam.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityDetailPinjamanAdminBinding
import com.hitzvera.kasbaik.response.ListBorrowerPendingResponseItem

class DetailPinjamanAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPinjamanAdminBinding
    private lateinit var token: String
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var vm: DetailPinjamanAdminViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPinjamanAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra("TOKEN").toString()
        id = intent.getStringExtra("IDUSER").toString()
        name = intent.getStringExtra("NAME").toString()
        vm = ViewModelProvider(this)[DetailPinjamanAdminViewModel::class.java]
        binding.apply {
            vm.getBorrowerById(token, id, this@DetailPinjamanAdminActivity)
            vm.list.observe(this@DetailPinjamanAdminActivity){
                if (it != null){
                    tvNamaPeminjam.text = name
                    if (it.creditApproval == 0){
                        tvCreditScore.text = "Not Evaluated Yet"
                    } else if (it.creditApproval == 1){
                        tvCreditScore.text = "Trusted"
                    } else {
                        tvCreditScore.text = "Kinda Trusted?"
                    }
                    tvPinjamanKe.text = it.pinjamanKe.toString()
                    tvReason.text = it.reasonBorrower
                    tvStatus.text = it.status
                    tvLoanAmount.text = "Rp. ${it.loanAmount}"
                    btnCancel.setOnClickListener {
                        finish()
                    }
                    btnSave.visibility = View.GONE
                }
            }
        }
    }

    private fun setData(item: ListBorrowerPendingResponseItem){
        binding.apply {

        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarDetailPinjaman.visibility = View.VISIBLE
        } else {
            binding.progressBarDetailPinjaman.visibility = View.GONE
        }
    }
}