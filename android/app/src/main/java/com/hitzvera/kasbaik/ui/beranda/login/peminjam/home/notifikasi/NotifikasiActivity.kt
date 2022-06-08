package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityNotifikasiBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.HistoryAdapter
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.detail.NotifikasiDetailActivity

class NotifikasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotifikasiBinding
    private lateinit var viewModel: NotifikasiViewModel
    private lateinit var token: String
    private var hasStopped: Boolean? = false

    private val adapter: NotifikasiAdapter by lazy {NotifikasiAdapter(NotifikasiAdapter.OnClickListener{ item ->
        Intent(this, NotifikasiDetailActivity::class.java).also {
            it.putExtra(TOKEN, token)
            it.putExtra(MESSAGE, item.message)
            it.putExtra(IDMESSAGE, item.idMessage)
            it.putExtra(DIBUAT, item.createdAt)
            startActivity(it)
        }
    })}

    override fun onStop() {
        super.onStop()
        hasStopped = true
    }

    override fun onResume() {
        super.onResume()
        if(hasStopped == true){
            recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hasStopped = false
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

    companion object {
        const val TOKEN = "token"
        const val MESSAGE = "message"
        const val DIBUAT = "dibuat"
        const val IDMESSAGE = "idmessage"
    }
}