package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityStatusBinding
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.HistoryAdapter
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.HistoryViewModel
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity

class StatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatusBinding
    private lateinit var token: String
    private lateinit var idBorrower: String
    private lateinit var viewModel: StatusViewModel
    private lateinit var currentBorrowing: Borrower
    private lateinit var viewModel2: HistoryViewModel

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
            if(it!=null){
                currentBorrowing = it.last()
                if(currentBorrowing.status == "done"){
                    setVisibility(false)
                } else if(currentBorrowing.status == "payment"){
                    setVisibility(true)
                    binding.tvLabelStatusHistoryTitle.visibility = View.VISIBLE
                    viewModel.getListPayment(token, this, currentBorrowing.idBorrower)
                    viewModel.listPayment.observe(this){ item ->
                        if(item!=null){
                            adapter.setData(item)
                            Log.e("test", item.toString())
                        }
                    }
                    viewModel.isLoading.observe(this){ item ->
                        showLoading(item)
                    }
                } else {
                    setVisibility(true)
                }

                binding.tvStatus.text = currentBorrowing.status
                binding.tvDibuat.text = currentBorrowing.createdAt
                binding.tvJumlahPinjaman.text = currentBorrowing.loanAmount.toString()
            }
        }



    }

    private fun setVisibility(visible: Boolean){
        if(visible){
            binding.tvLabelStatus.visibility = View.VISIBLE
            binding.tvStatus.visibility = View.VISIBLE
            binding.tvLabelDibuat.visibility = View.VISIBLE
            binding.tvDibuat.visibility = View.VISIBLE
            binding.tvJumlahPinjaman.visibility = View.VISIBLE
            binding.tvLabelJumlahPinjaman.visibility = View.VISIBLE
            binding.tvLabelNothing.visibility = View.GONE
        } else {
            binding.tvLabelStatus.visibility = View.GONE
            binding.tvStatus.visibility = View.GONE
            binding.tvLabelDibuat.visibility = View.GONE
            binding.tvDibuat.visibility = View.GONE
            binding.tvJumlahPinjaman.visibility = View.GONE
            binding.tvLabelJumlahPinjaman.visibility = View.GONE
            binding.tvLabelNothing.visibility = View.VISIBLE
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