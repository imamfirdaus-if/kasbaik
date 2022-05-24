package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.zakat

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityAboutBinding
import com.hitzvera.kasbaik.databinding.ActivityZakatBinding

class ZakatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZakatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZakatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.wvZakat
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                showLoading(true)
            }
            override fun onPageFinished(view: WebView, url: String) {
                showLoading(false)
            }
        }
        webView.loadUrl("https://kitabisa.com/explore/zakat")
    }
    fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarZakat.visibility = View.VISIBLE
        } else binding.progressBarZakat.visibility = View.GONE
    }
}