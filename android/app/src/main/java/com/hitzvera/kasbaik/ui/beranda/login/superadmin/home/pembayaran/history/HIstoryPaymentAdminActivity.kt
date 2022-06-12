package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pembayaran.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHistoryPaymentAdminBinding
import com.hitzvera.kasbaik.response.ListUserAdminResponseItem
import com.hitzvera.kasbaik.response.ProfilesItem
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna.ListPenggunaAdapter
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna.ListPenggunaViewModel
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna.profile.ProfileUserAdminViewModel

class HistoryPaymentAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryPaymentAdminBinding
    private lateinit var token: String
    private lateinit var idUser: String
    private lateinit var idBorrower: String
    private lateinit var vm1: HistoryPaymentAdminViewModel
    private lateinit var vm2: ProfileUserAdminViewModel
    private val adapter: HistoryPaymentAdminAdapter by lazy {
        HistoryPaymentAdminAdapter(HistoryPaymentAdminAdapter.OnClickListener{item ->
            //Do Nothing
        }, token, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryPaymentAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra("TOKEN").toString()
        idUser = intent.getStringExtra("IDUSER").toString()
        idBorrower = intent.getStringExtra("IDBORROWER").toString()
        vm1 = ViewModelProvider(this)[HistoryPaymentAdminViewModel::class.java]
        vm2 = ViewModelProvider(this)[ProfileUserAdminViewModel::class.java]
        binding.apply {
            rvListPayment.setHasFixedSize(true)
            rvListPayment.layoutManager = LinearLayoutManager(this@HistoryPaymentAdminActivity)
            rvListPayment.adapter = adapter
            vm2.getProfileUser(token, idUser, this@HistoryPaymentAdminActivity)
            vm2.list.observe(this@HistoryPaymentAdminActivity){
                val list: ProfilesItem = it
                titleListPayment.text = list.namaLengkap
            }
            vm1.getHistoryPayment(token, idBorrower, this@HistoryPaymentAdminActivity)
            vm1.list.observe(this@HistoryPaymentAdminActivity){
                if (it != null){
                    adapter.setData(it)
                }
                vm1.isLoading.observe(this@HistoryPaymentAdminActivity){
                    showLoading(it)
                }
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
}