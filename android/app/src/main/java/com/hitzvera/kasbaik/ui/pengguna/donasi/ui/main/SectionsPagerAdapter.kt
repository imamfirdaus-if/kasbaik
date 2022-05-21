package com.hitzvera.kasbaik.ui.pengguna.donasi.ui.main

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.ui.pengguna.donasi.ui.rutin.DRFragment
import com.hitzvera.kasbaik.ui.pengguna.donasi.ui.sekali.DSFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    @StringRes
    private val TAB = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)

    override fun getCount(): Int {
        val tab = 2
        return tab
    }

    override fun getItem(position: Int): Fragment {
        var page: Fragment? = null
        when(position){
            0 -> page = DSFragment()
            1 -> page = DRFragment()
        }
        return page as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB[position])
    }

}