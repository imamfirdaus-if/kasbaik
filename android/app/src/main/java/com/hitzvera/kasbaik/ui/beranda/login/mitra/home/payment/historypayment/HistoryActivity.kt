package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hitzvera.kasbaik.R

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
    }

    companion object{
        const val ID_BORROWER = "id_borrower"
        const val TOKEN = "token"
    }
}