package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat.detailriwayat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityDetailHistoryBinding
import com.hitzvera.kasbaik.databinding.ActivityStatusBinding
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.HistoryAdapter
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.HistoryViewModel
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat.HistoryActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.StatusAdapter
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.StatusViewModel

class DetailHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding
    private lateinit var token: String
    private lateinit var pinjamanKe: String
    private lateinit var idBorrower: String
    private lateinit var viewModel: DetailHistoryViewModel
    private lateinit var currentBorrowing: Borrower

    private val adapter: DetailHistoryAdapter by lazy {
        DetailHistoryAdapter(DetailHistoryAdapter.OnClickListener{ item ->
            //Do Nothing
        })
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(HistoryActivity.TOKEN) ?: ""
        val pembayaranKe = intent.getIntExtra(LOANCOUNT, 0)
        pinjamanKe = intent.getIntExtra(LOANCOUNT, 0).toString()
        idBorrower = intent.getStringExtra(ID).toString()
        binding.rvRiwayatPembayaran.layoutManager = LinearLayoutManager(this)
        binding.rvRiwayatPembayaran.adapter = adapter
        viewModel = ViewModelProvider(this)[DetailHistoryViewModel::class.java]
        viewModel.getListBorrowing(token, this, pinjamanKe)
        viewModel.listBorrowing.observe(this){
            if(it!=null){
                currentBorrowing = it[pembayaranKe-1]
                val tenor = currentBorrowing.tenor
                val income = currentBorrowing.monthlyIncome.toString()
                binding.tvStatus.text = currentBorrowing.status
                binding.tvPinjamanKe.text = currentBorrowing.pinjamanKe.toString()
                binding.tvJumlahPinjaman.text = currentBorrowing.loanAmount.toString()
                binding.tvTenorPinjaman.text = "$tenor Bulan"
                binding.tvPemasukan.text = "Rp. $income"
                binding.tvDeskripsi.text = currentBorrowing.reasonBorrower
                viewModel.getListPayment(token, this, idBorrower)
                viewModel.listPayment.observe(this){ item ->
                    if(item!=null){
                        adapter.setData(item)
                    }
                }
                viewModel.isLoading.observe(this){ item ->
                    showLoading(item)
                }
                binding.tvStatusPembayaran.text = currentBorrowing.status
                binding.btTutup.setOnClickListener {
                    finish()
                }
            }
        }



    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarHistoryPembayaran.visibility = View.VISIBLE
        } else {
            binding.progressBarHistoryPembayaran.visibility = View.GONE
        }
    }

    companion object{
        const val TOKEN = "token"
        const val ID = "id"
        const val LOANCOUNT = "pinjaman_ke"
    }
}