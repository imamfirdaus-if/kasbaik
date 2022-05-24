package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityHomePeminjamBinding

class HomePeminjamActivity : AppCompatActivity() {

    private lateinit var viewModel: HomePeminjamViewModel
    private lateinit var binding: ActivityHomePeminjamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePeminjamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(TOKEN)
        viewModel = ViewModelProvider(this)[HomePeminjamViewModel::class.java]
        viewModel.reqHomePeminjam(this, token!!)
        viewModel.homeUserResponse.observe(this){
            if(it!=null){
                binding.welcomeTitle.text = getString(R.string.greet_user, it.username)
            }
        }
        viewModel.isLoading.observe(this){
            if(it!=null){
                showLoading(it)
            }
        }
        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel(R.drawable.image_bantu_usaha, "The animal population decreased by 58 percent in 42 years."))
        imageList.add(SlideModel(R.drawable.image_berdayakan_sesama, "Elephants and tigers may become extinct."))
        imageList.add(SlideModel(R.drawable.image_bunga_0, "And people do that."))

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }
    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarHome.visibility = View.VISIBLE
        } else {
            binding.progressBarHome.visibility = View.GONE
        }
    }
    companion object {
        const val TOKEN = "token"
    }
}