package com.example.pablosj.testapppablosanjuan

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val JS_OBJECT = "javascript_obj"
    val pagina1 = "file:///android_asset/Page1.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar?.hide()
        Web_View.webViewClient = webViewcient()
        Web_View.settings.javaScriptEnabled = true
        Web_View.addJavascriptInterface(JavaScriptInterface(), JS_OBJECT)
        Button_Back.visibility = View.INVISIBLE
        Web_View.loadUrl(pagina1)
        Web_View.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (url == pagina1) {
                    WebJavaScriptDatos()
                }
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
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<   ON_DESTROY
    override fun onDestroy() {
        Web_View.removeJavascriptInterface(JS_OBJECT)
        super.onDestroy()
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<   INTERACCION_JAVA_SCRIPT
    private fun WebJavaScriptDatos() {
        Web_View.loadUrl("javascript: " +
                "window.androidObj.AndroidUpdate = function(message) { " +
                JS_OBJECT + ".DesdeJS(message) }")
    }
    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun DesdeJS(tag: String) {
            LoadContent(tag)
        }
    }
    fun LoadContent(tag:String) {
        val intent = Intent(this,Main2Activity::class.java)
        intent.putExtra("dato_item", tag)
        startActivity(intent)
    }
}