package com.lefor.bard

import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.ProxyConfig
import androidx.webkit.ProxyController


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWebView: WebView = findViewById(R.id.main_view)

        myWebView.settings.javaScriptEnabled = true // enable JS

        setProxy() // connect proxy

        //val webView = WebView(applicationContext)
        myWebView.setWebViewClient(MyWebViewClient())
        myWebView.loadUrl("https://bard.google.com")
        Handler().postDelayed({
            myWebView.reload() // Due to problems with the proxy, the page loads 2 times
        }, 3000)

        /*val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            //myWebView.loadUrl("https://bard.google.com/chat")
            myWebView.reload()

            // there was a page reload button
        }*/

    }
    private fun setProxy() { // Function copied from stax overflow
        val proxyConfig = ProxyConfig.Builder()
            .addProxyRule("35.236.207.242:33333")
            .addDirect().build()
        ProxyController.getInstance().setProxyOverride(proxyConfig, {
            //do nothing
        }) { }
    }
}


class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        // Если ссылка начинается с `http://` или `https://`, откройте ее в webview.
        if (url.startsWith("http://") || url.startsWith("https://")) {
            view.loadUrl(url)
            return true
        }

        // Если ссылка не начинается с `http://` или `https://`, откройте ее в Chrome.
        return false
    }
}
