package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.databinding.ActivityListPeminjamBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.detailpinjaman.DetailPinjamanActivity

class ListPeminjamActivity : AppCompatActivity() {

    private lateinit var token: String
    private lateinit var binding: ActivityListPeminjamBinding
    private lateinit var viewModel: ListPeminjamViewModel
    private val listPeminjamAdapter: ListPeminjamAdapter by lazy {
        ListPeminjamAdapter(ListPeminjamAdapter.OnClickListener{ item ->
            Intent(this, DetailPinjamanActivity::class.java).also {
                it.putExtra(DetailPinjamanActivity.NAMA_PEMINJAM, item.namaLengkap)
                it.putExtra(DetailPinjamanActivity.CREDIT_SCORE, item.creditScore)
                it.putExtra(DetailPinjamanActivity.PINJAMAN_KE, item.pinjamanKe)
                it.putExtra(DetailPinjamanActivity.LOAN_AMOUNT, item.loanAmount)
                it.putExtra(DetailPinjamanActivity.REASON, item.reasonBorrower)
                it.putExtra(DetailPinjamanActivity.TOKEN, token)
                it.putExtra(DetailPinjamanActivity.MONTLY_INCOME, item.monthlyIncome)
                it.putExtra(DetailPinjamanActivity.ID_BORROWER, item.idBorrower)
                startActivity(it)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPeminjamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(HomeMitraActivity.TOKEN)!!
        binding.rvListPeminjam.layoutManager = LinearLayoutManager(this)
        binding.rvListPeminjam.adapter = listPeminjamAdapter
        viewModel = ViewModelProvider(this)[ListPeminjamViewModel::class.java]
        viewModel.apply {
            getListBorrwer(token, this@ListPeminjamActivity)
            listBorrower.observe(this@ListPeminjamActivity){
                if(it!=null){
                    listPeminjamAdapter.setData(it)
                }
            }
            isLoading.observe(this@ListPeminjamActivity){
                showLoading(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarListPinjaman.visibility = View.VISIBLE
        } else {
            binding.progressBarListPinjaman.visibility = View.GONE
        }
    }
}