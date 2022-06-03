package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityStatusBinding
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.HistoryAdapter
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity

class StatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatusBinding
    private lateinit var token: String
    private lateinit var idBorrower: String
    private lateinit var viewModel: StatusViewModel
    private lateinit var currentBorrowing: Borrower

    private val adapter: StatusAdapter by lazy {
        StatusAdapter(StatusAdapter.OnClickListener{ item ->
            //Do Nothing
        })
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(HomePeminjamActivity.TOKEN) ?: ""
        binding.rvHistoryPayment.layoutManager = LinearLayoutManager(this)
        binding.rvHistoryPayment.adapter = adapter
        viewModel = ViewModelProvider(this)[StatusViewModel::class.java]

        viewModel.getListBorrowing(token, this)
        viewModel.listBorrowing.observe(this){
            idBorrower = it?.last()?.idBorrower ?: "error"
            currentBorrowing = it.last()
            viewModel.getListPayment(token, this, idBorrower)
            viewModel.listPayment.observe(this){ item ->
                if(item!=null){
                    adapter.setData(item)
                }
            }
            binding.tvStatus.text = currentBorrowing.status
            binding.tvDibuat.text = currentBorrowing.createdAt
            binding.tvJumlahPinjaman.text = currentBorrowing.loanAmount.toString()
        }







    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarHistoryPembayaran.visibility = View.VISIBLE
        } else {
            binding.progressBarHistoryPembayaran.visibility = View.GONE
        }
    }
}