package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityStatusEditBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.StatusActivity

class StatusEditActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var token: String
    private lateinit var idBorrower: String
    private lateinit var reasonBorrower: String
    private var loanAmount: Int = 0
    private var monthlyIncome: Int = 0
    private var dependentAmount: Int = 0
    private var donasi: Int = 0
    private var tenor: Int = 0

    private lateinit var binding: ActivityStatusEditBinding
    private lateinit var viewModel: StatusEditViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[StatusEditViewModel::class.java]
        getData()
        bindData()

    }
    private fun getData(){
        token = intent.getStringExtra(StatusActivity.TOKEN).toString()
        idBorrower = intent.getStringExtra(StatusActivity.ID_BORROWER).toString()
        reasonBorrower = intent.getStringExtra(StatusActivity.REASON_BORROWER).toString()
        loanAmount = intent.getIntExtra(StatusActivity.LOAN_AMOUNT, 0)
        donasi = intent.getIntExtra(StatusActivity.DONASI, 0)
        monthlyIncome = intent.getIntExtra(StatusActivity.MONTHLY_INCOME, 0)
        dependentAmount = intent.getIntExtra(StatusActivity.DEPENDENT_AMOUNT, 0)
        tenor = intent.getIntExtra(StatusActivity.TENOR, 0)
    }
    private fun bindData(){
        binding.tvJumlahPinjaman.text = loanAmount.toString()
        binding.tvTenor.text = tenor.toString()
        binding.tvPemasukan.setText(monthlyIncome.toString())
        binding.tvReason.setText(reasonBorrower)
        binding.tvDonasi.setText(donasi.toString())
        binding.tvTanggungan.setText(dependentAmount.toString())
        binding.btnEdit.setOnClickListener(this)
        binding.btnAdd.setOnClickListener(this)
        binding.btnAddTenor.setOnClickListener(this)
        binding.btnMinus.setOnClickListener(this)
        binding.btnMinusTenor.setOnClickListener(this)
    }
    private fun validateForm(): Boolean{
        return (binding.tvPemasukan.text.isNotBlank()
                && binding.tvTanggungan.text.isNotBlank()
                && binding.tvReason.text.isNotBlank()
                && loanAmount != 0)
    }
    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarPinjamDana.visibility = View.VISIBLE
        } else {
            binding.progressBarPinjamDana.visibility = View.GONE
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_edit -> {
                if(validateForm()){
                    viewModel.editRequestBorrowing(
                        token, idBorrower, loanAmount, reasonBorrower, tenor, monthlyIncome, donasi, this
                    )
                    viewModel.isLoading.observe(this){
                        showLoading(it)
                        if(it){
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this, "Data have to be valid", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_add -> {
                if(binding.tvJumlahPinjaman.text.toString().toInt() < 3_000_000){
                    loanAmount+=500_000
                }
                binding.tvJumlahPinjaman.text = loanAmount.toString()
            }
            R.id.btn_minus -> {
                if(binding.tvJumlahPinjaman.text.toString().toInt() > 0){
                    loanAmount-=500_000
                }
                binding.tvJumlahPinjaman.text = loanAmount.toString()
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
        }
    }

}