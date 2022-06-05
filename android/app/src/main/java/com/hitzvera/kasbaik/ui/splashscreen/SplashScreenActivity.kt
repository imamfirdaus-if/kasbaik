package com.hitzvera.kasbaik.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hitzvera.kasbaik.MainActivity
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.ui.dashboard.daftar.DashboardDaftarActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this, DashboardDaftarActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}
