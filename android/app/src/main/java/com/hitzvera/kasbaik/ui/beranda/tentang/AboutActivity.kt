package com.hitzvera.kasbaik.ui.beranda.tentang

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.aboutWebview
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                showLoading(true)
            }
            override fun onPageFinished(view: WebView, url: String) {
                showLoading(false)
            }
        }
        webView.loadUrl("https://kasbaik.kitabisa.com/")
    }
    fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else binding.progressBar.visibility = View.GONE
    }
}