package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status

import android.content.Intent
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
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.edit.StatusEditActivity

class StatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatusBinding
    private lateinit var token: String
    private lateinit var viewModel: StatusViewModel
    private lateinit var currentBorrowing: Borrower

    private var hasStopped = false

    private val adapter: StatusAdapter by lazy {
        StatusAdapter(StatusAdapter.OnClickListener{ item ->
            //Do Nothing
        })
    }

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
        binding = ActivityStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hasStopped = false
        token = intent.getStringExtra(HomePeminjamActivity.TOKEN) ?: ""
        binding.rvHistoryPayment.layoutManager = LinearLayoutManager(this)
        binding.rvHistoryPayment.adapter = adapter
        viewModel = ViewModelProvider(this)[StatusViewModel::class.java]

        viewModel.getListBorrowing(token, this)
        viewModel.listBorrowing.observe(this){ it ->
            if(!it.isNullOrEmpty()){
                currentBorrowing = it.last()
                if(currentBorrowing.status == "done"){
                    setVisibility(false)
                } else if(currentBorrowing.status == "payment"){
                    setVisibility(true)
                    binding.tvLabelStatusHistoryTitle.visibility = View.VISIBLE
                    viewModel.getListPayment(token, this, currentBorrowing.idBorrower)
                    viewModel.listPayment.observe(this){ item ->
                        if(!item.isNullOrEmpty()){
                            adapter.setData(item)
                            Log.e("test", item.toString())
                        } else {
                            binding.containerPaymentNone.visibility = View.VISIBLE
                        }
                    }
                    viewModel.isLoading.observe(this){ item ->
                        showLoading(item)
                    }
                } else if(currentBorrowing.status == "pending"){
                    setVisibility(false)
                    binding.btnDelete.visibility = View.VISIBLE
                    binding.btnEdit.visibility = View.VISIBLE
                    binding.btnEdit.setOnClickListener {
                        Intent(this, StatusEditActivity::class.java).also {
                            it.putExtra(TOKEN, token)
                            it.putExtra(ID_BORROWER, currentBorrowing.idBorrower)
                            it.putExtra(LOAN_AMOUNT, currentBorrowing.loanAmount)
                            it.putExtra(REASON_BORROWER, currentBorrowing.reasonBorrower)
                            it.putExtra(TENOR, currentBorrowing.tenor)
                            it.putExtra(MONTHLY_INCOME, currentBorrowing.monthlyIncome)
                            it.putExtra(DONASI, currentBorrowing.donasi)
                            it.putExtra(DEPENDENT_AMOUNT, currentBorrowing.dependentsAmount)
                            startActivity(it)
                        }
                    }
                    binding.btnDelete.setOnClickListener {
                        viewModel.deleteRequest(token, currentBorrowing.idBorrower, this)
                        viewModel.isLoading.observe(this){
                            showLoading(it)
                        }
                        viewModel.isSuccessful.observe(this){
                            if(it){
                                recreate()
                            }
                        }
                    }
                } else {
                    setVisibility(true)
                }
                binding.tvStatus.text = currentBorrowing.status
                binding.tvDibuat.text = currentBorrowing.createdAt
                binding.tvJumlahPinjaman.text = currentBorrowing.loanAmount.toString()
            } else {
                setVisibility(false)
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
        binding.btnEdit.visibility = View.GONE
        binding.btnDelete.visibility = View.GONE
        binding.containerPaymentNone.visibility = View.GONE
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarHistoryPembayaran.visibility = View.VISIBLE
        } else {
            binding.progressBarHistoryPembayaran.visibility = View.GONE
        }
    }

    companion object {
        const val TOKEN = "token"
        const val ID_BORROWER = "id_borrower"
        const val LOAN_AMOUNT = "loan_amount"
        const val REASON_BORROWER = "reason_borrower"
        const val TENOR = "tenor"
        const val DEPENDENT_AMOUNT = "dependent_amount"
        const val MONTHLY_INCOME = "monthly_income"
        const val DONASI = "donasi"
    }
}