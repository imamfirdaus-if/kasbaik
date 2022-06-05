package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.databinding.ActivityHistory2Binding
import com.hitzvera.kasbaik.databinding.ActivityPaymentBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.ListAdapter
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.PaymentViewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var token: String
    private lateinit var binding: ActivityHistory2Binding
    private lateinit var viewModel: HistoryViewModel
    private val Adapter: HistoryAdapter by lazy {
        HistoryAdapter(HistoryAdapter.OnClickListener{ item ->
            //Do Nothing
        }, this, token)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistory2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(TOKEN)!!
        binding.rvRiwayatPinjaman.layoutManager = LinearLayoutManager(this)
        binding.rvRiwayatPinjaman.adapter = Adapter
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        viewModel.apply {
            getListPinjaman(token, this@HistoryActivity)
            listBorrower.observe(this@HistoryActivity){
                if(it!=null){
                    Adapter.setData(it.filter { item -> item.status == "done" })
                }
            }
            isLoading.observe(this@HistoryActivity){
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


    companion object{
        const val TOKEN = "token"
    }
}