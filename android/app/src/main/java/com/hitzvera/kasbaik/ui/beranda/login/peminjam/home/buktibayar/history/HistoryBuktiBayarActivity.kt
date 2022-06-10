package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHistoryBuktiBayarBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.UploadBuktiBayarActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiAdapter
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.detail.NotifikasiDetailActivity

class HistoryBuktiBayarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBuktiBayarBinding
    private lateinit var token: String
    private lateinit var viewModel: HistoryBuktiBayarViewModel
    private val adapter: HistoryBuktiBayarAdapter by lazy {
        HistoryBuktiBayarAdapter(HistoryBuktiBayarAdapter.OnClickListener{ item ->
        Intent(this, DetailHistoryBuktiBayarActivity::class.java).also {
            it.putExtra(NotifikasiActivity.TOKEN, token)
            it.putExtra(NotifikasiActivity.MESSAGE, item.message)
            it.putExtra(NotifikasiActivity.DIBUAT, item.createdAt)
            it.putExtra(UploadBuktiBayarActivity.LINKBUKTI, item.linkBukti)
            startActivity(it)
        }
    })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBuktiBayarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(UploadBuktiBayarActivity.TOKEN).toString()
        viewModel = ViewModelProvider(this)[HistoryBuktiBayarViewModel::class.java]
        binding.rvHistoryBuktiBayar.layoutManager = LinearLayoutManager(this)
        binding.rvHistoryBuktiBayar.adapter = adapter
        viewModel.getBuktiBayar(token, this)
        viewModel.buktiBayarResponse.observe(this){
            adapter.setData(it.filter { item -> !item.linkBukti.isNullOrBlank() })
        }
    }
}