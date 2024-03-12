package com.alamanah.webview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.alamanah.webview.component.WebViewLoader
import com.alamanah.webview.ui.theme.AlamanahTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val url =  "https://alamanah.xyz/"
        super.onCreate(savedInstanceState)
        setContent {
            AlamanahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        content = { padding ->
                            WebViewLoader(url = url , padding)
                        }
                    )
                }
            }
        }
    }
}