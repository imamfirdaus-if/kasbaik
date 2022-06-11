package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.databinding.ActivityListPeminjamBinding
import com.hitzvera.kasbaik.databinding.ActivityPaymentBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.ListPeminjamAdapter
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.ListPeminjamViewModel
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.detailpinjaman.DetailPinjamanActivity

class PaymentActivity : AppCompatActivity() {

    private lateinit var token: String
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var viewModel: PaymentViewModel
    private var hasStoped = false
    private val Adapter: ListAdapter by lazy {
        ListAdapter(ListAdapter.OnClickListener{ item ->
            Toast.makeText(this, "Untuk mengubah status, mohon gunakan menu list peminjam", Toast.LENGTH_LONG).show()
        }, this, token)
    }

    override fun onStop() {
        super.onStop()
        hasStoped = true
    }

    override fun onResume() {
        super.onResume()
        if(hasStoped){
            recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hasStoped = false
        token = intent.getStringExtra(HomeMitraActivity.TOKEN)!!
        binding.rvListPeminjam.layoutManager = LinearLayoutManager(this)
        binding.rvListPeminjam.adapter = Adapter
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        viewModel.apply {
            getListBorrwer(token, this@PaymentActivity)
            listBorrower.observe(this@PaymentActivity){
                if(it!=null){
                    Adapter.setData(it)
                }
            }
            isLoading.observe(this@PaymentActivity){
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

    fun finishMe() { finish()}

    companion object{
        const val TOKEN = "token"
    }
}