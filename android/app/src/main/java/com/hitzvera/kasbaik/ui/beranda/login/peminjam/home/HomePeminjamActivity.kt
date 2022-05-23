package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.hitzvera.kasbaik.R

class HomePeminjamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_peminjam)

        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(R.drawable.image_bantu_usaha, "The animal population decreased by 58 percent in 42 years."))
        imageList.add(SlideModel(R.drawable.image_berdayakan_sesama, "Elephants and tigers may become extinct."))
        imageList.add(SlideModel(R.drawable.image_bunga_0, "And people do that."))

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }
}