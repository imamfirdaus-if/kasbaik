package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.detailpayment

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityDetailPaymentBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.ListPeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.detailpinjaman.DetailPinjamanActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.PaymentActivity

class DetailPaymentActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailPaymentBinding
    private lateinit var viewModel: DetailPaymentViewModel
    private lateinit var token: String
    private lateinit var idBorrower: String
    private lateinit var creditScore: String
    private lateinit var namaPeminjam: String
    private lateinit var status: String
    private lateinit var loanAmount: String

    override fun onResume() {
        super.onResume()
        val method = resources.getStringArray(R.array.method)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, method)
        binding.methodContainer.setAdapter(arrayAdapter)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailPaymentViewModel::class.java]
        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        getData()
        setData()
    }

    private fun getData(){
        token = intent.getStringExtra(TOKEN).toString()
        idBorrower = intent.getStringExtra(ID_BORROWER).toString()
        creditScore = intent.getIntExtra(CREDIT_SCORE, 0).toString()
        namaPeminjam = intent.getStringExtra(NAMA_PEMINJAM).toString()
        status = intent.getStringExtra(STATUS).toString()
        loanAmount = intent.getIntExtra(LOAN_AMOUNT, 0).toString()
    }

    private fun setData() {
        binding.apply {
            tvNamaPeminjam.text = namaPeminjam
            tvCreditScore.text = creditScore
            tvStatus.text = status
            tvPinjaman.text = loanAmount
        }
    }

    override fun onClick(view: View){
        when(view.id){
            R.id.btn_save -> {
                val amountPayment = binding.etPayment.text.toString()
                val method = binding.methodContainer.text.toString()
                viewModel.apply {
                    addPayment(token, idBorrower, amountPayment.toInt(), method, this@DetailPaymentActivity)

                    isLoading.observe(this@DetailPaymentActivity){
                        showLoading(it)
                    }
                }
                Log.e("Cek", method)
            }
            R.id.btn_cancel -> {
                finish()
            }
        }
    }


    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarDetailPinjaman.visibility = View.VISIBLE
        } else {
            binding.progressBarDetailPinjaman.visibility = View.GONE
        }
    }

    companion object{
        const val NAMA_PEMINJAM = "nama peminjam"
        const val CREDIT_SCORE = "credit_score"
        const val STATUS = "status"
        const val LOAN_AMOUNT = "loan_amount"
        const val TOKEN = "token"
        const val ID_BORROWER = "id_borrower"
        const val ID_MITRA = "id_mitra"
    }
}