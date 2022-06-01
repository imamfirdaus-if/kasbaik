package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.detailpinjaman

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
import com.hitzvera.kasbaik.databinding.ActivityDetailPinjamanBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.ListPeminjamActivity

class DetailPinjamanActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailPinjamanBinding
    private lateinit var viewModel: DetailPinjamanViewModel
    private lateinit var token: String
    private lateinit var idBorrower: String
    private lateinit var creditScore: String
    private lateinit var namaPeminjam: String
    private var pinjamanKe: Int = 0
    private var loanAmount: Int = 0
    private lateinit var reason: String

    override fun onResume() {
        super.onResume()
        val status = resources.getStringArray(R.array.status)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, status)
        binding.statusContainer.setAdapter(arrayAdapter)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPinjamanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailPinjamanViewModel::class.java]
        binding.btnSave.setOnClickListener(this)
        getData()
        setData()
    }

    private fun getData(){
        token = intent.getStringExtra(TOKEN).toString()
        namaPeminjam = intent.getStringExtra(NAMA_PEMINJAM).toString()
        creditScore = intent.getStringExtra(CREDIT_SCORE).toString()
        pinjamanKe = intent.getIntExtra(PINJAMAN_KE, 0)
        loanAmount = intent.getIntExtra(LOAN_AMOUNT, 0)
        reason = intent.getStringExtra(REASON).toString()
        idBorrower = intent.getStringExtra(ID_BORROWER).toString()
    }
    private fun setData(){
        binding.apply {
            tvNamaPeminjam.text = namaPeminjam
            tvCreditScore.text = creditScore
            tvPinjamanKe.text = pinjamanKe.toString()
            tvLoanAmount.text = loanAmount.toString()
            tvReason.text = reason
        }
    }

    companion object {
        const val TOKEN = "token"
        const val ID_BORROWER = "id_borrower"
        const val CREDIT_SCORE = "credit_score"
        const val PINJAMAN_KE = "pinjaman_ke"
        const val LOAN_AMOUNT = "loan_amount"
        const val REASON = "reason"
        const val NAMA_PEMINJAM = "nama_peminjam"
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_save -> {
                val status = binding.statusContainer.text.toString()
                viewModel.apply {
                    updateStatusRequest(token, status, idBorrower)
                    isSuccessful.observe(this@DetailPinjamanActivity){
                        showCustomDialog(it)
                    }
                    isLoading.observe(this@DetailPinjamanActivity){
                        showLoading(it)
                    }
                }
                Log.e("CEK", status.toString())
            }
        }
    }

    private fun showCustomDialog(state: String){
        if(state == "success"){
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.register_pop_up_success)
            val btnLogin: Button = dialog.findViewById(R.id.btn_login)
            val tvSuccessful: TextView = dialog.findViewById(R.id.tv_successful)
            tvSuccessful.text = "Berhasil menyimpan status"
            btnLogin.visibility = View.GONE
            dialog.setOnDismissListener {
                Intent(this, ListPeminjamActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    startActivity(it)
                }
            }
            dialog.show()
        } else if(state == "failed") {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.register_pop_up_failed)
            viewModel.errorMessage.observe(this){
                val tvErrorMessage: TextView = dialog.findViewById(R.id.tv_error_message)
                tvErrorMessage.text = it
            }
            val retryBtn: Button = dialog.findViewById(R.id.btn_retry)
            retryBtn.setOnClickListener { dialog.dismiss() }
            dialog.show()
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