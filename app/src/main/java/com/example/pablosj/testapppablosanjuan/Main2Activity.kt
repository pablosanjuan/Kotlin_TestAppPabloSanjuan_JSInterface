package com.example.pablosj.testapppablosanjuan

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class Main2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Dato_item : String = intent.getStringExtra("dato_item")
        textView_Titulo.text = Dato_item
        val actionBar = supportActionBar
        actionBar?.hide()

        Web_View.webViewClient = Main2Activity.webViewcient()
        Web_View.settings.javaScriptEnabled = true
        Web_View.loadUrl("file:///android_asset/Page2.html")
        SendInfo(Dato_item)

        Button_Back.setOnClickListener() {
            val intent = Intent(this,MainActivity::class.java)
            sendBroadcast(intent)
            finish()
        }
    }
    fun SendInfo(dato_item: String) {
        Web_View.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                Web_View.evaluateJavascript("javascript: " +
                        "WebViewUpdate(\"" + dato_item + "\")",
                        null)
            }
        }
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<   WEB_CLIENT
    class webViewcient : WebViewClient() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return true
        }
    }
}