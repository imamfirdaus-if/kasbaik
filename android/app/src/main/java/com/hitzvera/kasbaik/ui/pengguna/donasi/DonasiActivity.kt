package com.hitzvera.kasbaik.ui.pengguna.donasi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hitzvera.kasbaik.databinding.ActivityDonasiBinding
import com.hitzvera.kasbaik.ui.pengguna.donasi.ui.main.SectionsPagerAdapter


class DonasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonasiBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val pager = SectionsPagerAdapter(applicationContext, supportFragmentManager)
            vpFoll.adapter = pager
            tlFoll.setupWithViewPager(vpFoll)

//            val vp: ViewPager2 = binding.viewPager
//            vp.adapter = sPA
//            val tabs: TabLayout = binding.tabs
//            TabLayoutMediator(tabs, vp) { tab, position ->
//                tab.text = resources.getString(TAB_TITLES[position])
//            }.attach()
//
//            supportActionBar?.elevation = 0f
        }


    }

}