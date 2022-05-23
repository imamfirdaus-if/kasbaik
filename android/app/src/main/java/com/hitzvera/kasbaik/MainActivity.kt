package com.hitzvera.kasbaik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hitzvera.kasbaik.databinding.ActivityMainBinding
import com.hitzvera.kasbaik.ui.beranda.BerandaActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.start -> {
                val intent = Intent(this, BerandaActivity::class.java)
                startActivity(intent)
            }
        }
    }
}