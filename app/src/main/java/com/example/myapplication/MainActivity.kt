package com.example.myapplication // MANTENHA SEU PACKAGE ORIGINAL AQUI

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                WebViewScreen()
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen() {
    // URL DO SEU SITE (Certifique-se que é a URL correta do Firebase)
    val url = "https://curso-2---trabalho-em-equipe.web.app/pages/login.html"

    // Variáveis para upload de arquivos
    var filePathCallback by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }

    val fileChooserLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK && result.data != null) {
            val uriResult = WebChromeClient.FileChooserParams.parseResult(result.resultCode, result.data)
            filePathCallback?.onReceiveValue(uriResult)
        } else {
            filePathCallback?.onReceiveValue(null)
        }
        filePathCallback = null
    }

    var webView: WebView? by remember { mutableStateOf(null) }

    BackHandler(enabled = true) {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                // --- AQUI ESTÁ A MÁGICA DA RESPONSIVIDADE ---
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    databaseEnabled = true
                    allowFileAccess = true
                    mediaPlaybackRequiresUserGesture = false
                    cacheMode = WebSettings.LOAD_DEFAULT
                    userAgentString = userAgentString.replace("; wv", "")

                    // === AS LINHAS MÁGICAS DA RESPONSIVIDADE ===
                    useWideViewPort = true          // 1. Habilita o suporte a meta tag viewport
                    loadWithOverviewMode = true     // 2. Ajusta o conteúdo à largura da tela
                    layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING // 3. Ajusta tamanho do texto

                    // Opcional: Permite zoom de pinça (pinch-to-zoom) se o site ficar pequeno
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false
                }

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        val link = request?.url.toString()
                        if (link.contains("firebaseapp.com") || link.contains("web.app") || link.contains("accounts.google.com")) {
                            return false
                        }
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return true
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallbackParam: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean {
                        filePathCallback?.onReceiveValue(null)
                        filePathCallback = filePathCallbackParam
                        try {
                            val intent = fileChooserParams?.createIntent()
                            if (intent != null) {
                                fileChooserLauncher.launch(intent)
                            }
                        } catch (e: Exception) {
                            filePathCallback = null
                            return false
                        }
                        return true
                    }
                }

                loadUrl(url)
                webView = this
            }
        },
        update = {
            webView = it
        }
    )
}