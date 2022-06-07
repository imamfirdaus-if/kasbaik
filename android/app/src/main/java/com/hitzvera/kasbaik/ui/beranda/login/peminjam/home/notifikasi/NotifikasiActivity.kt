package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityNotifikasiBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.HistoryAdapter
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity

class NotifikasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotifikasiBinding
    private lateinit var viewModel: NotifikasiViewModel
    private lateinit var token: String

    private val adapter: NotifikasiAdapter by lazy {NotifikasiAdapter()}
//    {
//        NotifikasiAdapter(NotifikasiAdapter.OnClickListener{ item ->
//            //Do Nothing
//        })
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(HomePeminjamActivity.TOKEN).toString()
        viewModel = ViewModelProvider(this)[NotifikasiViewModel::class.java]
        binding.rvListMessage.adapter = adapter
        binding.rvListMessage.layoutManager = LinearLayoutManager(this)
        viewModel.getListMessage(token, this)
        viewModel.listMessage.observe(this){
            if(!it.isNullOrEmpty()){
                adapter.setData(it)
            }
        }

    }
}