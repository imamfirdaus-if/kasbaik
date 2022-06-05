package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.pinjamdana

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityPinjamDanaBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity

class PinjamDanaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPinjamDanaBinding
    private lateinit var viewModel: PinjamDanaViewModel
    private lateinit var token: String
    private var jumlahPinjaman = 0
    private var tenor = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinjamDanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(HomePeminjamActivity.TOKEN).toString()
        viewModel = ViewModelProvider(this)[PinjamDanaViewModel::class.java]

        binding.apply {
            tvJumlahPinjaman.text = jumlahPinjaman.toString()
            tvTenor.text = tenor.toString()
            btnAdd.setOnClickListener(this@PinjamDanaActivity)
            btnMinus.setOnClickListener(this@PinjamDanaActivity)
            btnAjukanPinjaman.setOnClickListener(this@PinjamDanaActivity)
            btnAddTenor.setOnClickListener(this@PinjamDanaActivity)
            btnMinusTenor.setOnClickListener(this@PinjamDanaActivity)
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_add -> {
                if(binding.tvJumlahPinjaman.text.toString().toInt() < 3_000_000){
                    jumlahPinjaman+=500_000
                }
                binding.tvJumlahPinjaman.text = jumlahPinjaman.toString()
            }
            R.id.btn_minus -> {
                if(binding.tvJumlahPinjaman.text.toString().toInt() > 0){
                    jumlahPinjaman-=500_000
                }
                binding.tvJumlahPinjaman.text = jumlahPinjaman.toString()
            }
            R.id.btn_minus_tenor -> {
                if(binding.tvTenor.text.toString().toInt() > 2){
                    tenor-=1
                }
                binding.tvTenor.text = tenor.toString()
            }
            R.id.btn_add_tenor -> {
                if(binding.tvTenor.text.toString().toInt() < 20){
                    tenor+=1
                }
                binding.tvTenor.text = tenor.toString()
            }
            R.id.btn_ajukan_pinjaman -> {
                val jumlahPinjaman = jumlahPinjaman
                val monthlyIncome = Integer.parseInt(binding.tvPemasukan.text.toString().trim())
                val dependentsAmount = Integer.parseInt(binding.tvTanggungan.text.toString().trim())
                val reason = binding.tvReason.text.toString().trim()
                var paymentMethod = "cicil"
                var donasi = 0
                if(binding.tvDonasi.text.toString().isNotBlank()){
                    donasi = binding.tvDonasi.text.toString().toInt()
                }
                if(validateForm()){
                    Log.e("CHECK", "$jumlahPinjaman $reason $monthlyIncome $paymentMethod, $tenor $dependentsAmount")
                    viewModel.apply {
                        postBorrower(token, jumlahPinjaman, reason, monthlyIncome, paymentMethod, tenor, dependentsAmount, donasi,this@PinjamDanaActivity)
                        isLoading.observe(this@PinjamDanaActivity){
                            showLoading(it)
                        }
                        isSuccessful.observe(this@PinjamDanaActivity){
                            showCustomDialog(it)
                        }
                    }
                } else {
                    Toast.makeText(this, "Masukan data yang benar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarPinjamDana.visibility = View.VISIBLE
        } else {
            binding.progressBarPinjamDana.visibility = View.GONE
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
            tvSuccessful.text = "Peminjaman Berhasil"
            btnLogin.visibility = View.GONE
            dialog.setOnDismissListener {
                Intent(this, HomePeminjamActivity::class.java).also {
                    it.putExtra(HomePeminjamActivity.TOKEN, token)
                    startActivity(it)
                    finish()
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

    private fun validateForm(): Boolean{
        return (
            jumlahPinjaman != 0
            && binding.tvTenor.text.isNotBlank()
            && binding.tvPemasukan.text.isNotBlank()
            && binding.tvTanggungan.text.isNotBlank()
            && binding.tvReason.text.isNotBlank()
        )
    }

}