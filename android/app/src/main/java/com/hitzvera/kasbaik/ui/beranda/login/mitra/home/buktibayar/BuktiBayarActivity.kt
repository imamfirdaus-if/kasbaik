package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.buktibayar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityBuktiBayarBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.UploadBuktiBayarActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.history.DetailHistoryBuktiBayarActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.history.HistoryBuktiBayarAdapter
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.history.HistoryBuktiBayarViewModel
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiAdapter
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiViewModel
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.detail.NotifikasiDetailActivity

class BuktiBayarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuktiBayarBinding
    private lateinit var token: String
    private lateinit var viewModel: NotifikasiViewModel
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
        binding = ActivityBuktiBayarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(HomeMitraActivity.TOKEN).toString()
        viewModel = ViewModelProvider(this)[NotifikasiViewModel::class.java]
        binding.rvBuktiBayar.adapter = adapter
        binding.rvBuktiBayar.layoutManager = LinearLayoutManager(this)
        viewModel.getListMessage(token, this)
        viewModel.listMessage.observe(this){
            if(it!=null){
                adapter.setData(it.filter{ item -> !item.linkBukti.isNullOrBlank()})
            }
        }

    }
}