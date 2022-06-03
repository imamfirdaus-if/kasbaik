package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHistoryBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.PaymentActivity

class HistoryActivity : AppCompatActivity() {
    private lateinit var token: String
    private lateinit var idBorrower: String
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter(HistoryAdapter.OnClickListener{ item ->
            //Do Nothing
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idBorrower = intent.getStringExtra(ID_BORROWER).toString()
        token = intent.getStringExtra(PaymentActivity.TOKEN)!!
        binding.rvListPayment.layoutManager = LinearLayoutManager(this)
        binding.rvListPayment.adapter = adapter
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        viewModel.apply {
            getListPayment(token, this@HistoryActivity, idBorrower)
            listPayment.observe(this@HistoryActivity){
                if (it != null){
                    adapter.setData(it)
                }
            }
            isLoading.observe(this@HistoryActivity){
                showLoading(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarListPayment.visibility = View.VISIBLE
        } else {
            binding.progressBarListPayment.visibility = View.GONE
        }
    }

    companion object{
        const val ID_BORROWER = "id_borrower"
        const val NAMA = "nama_peminjam"
        const val TOKEN = "token"
    }
}