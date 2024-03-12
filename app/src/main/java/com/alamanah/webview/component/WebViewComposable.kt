package com.alamanah.webview.component

import ProgressBar
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alamanah.webview.R
@Composable
fun WebViewLoader(url: String, padding: PaddingValues) {

    //..........................................................................custom full screen dialog
    val loaderDialogScreen = remember { mutableStateOf(true) }
    val openFullDialogCustom = remember { mutableStateOf(false) }
    val refreshScreen = remember { mutableStateOf(false) }
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    var progressValue by remember {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current


    if (loaderDialogScreen.value) {
        // Dialog function
        Dialog(
            onDismissRequest = {
                loaderDialogScreen.value = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo"
                )
                Spacer(modifier = Modifier.height(40.dp))
                ProgressBar(value = progressValue)
                Spacer(modifier = Modifier.height(20.dp))

                //.........................Text : description
                Text(
                    text = "Please wait...",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    letterSpacing = 3.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF4CAF50) //MaterialTheme.colorScheme.background,
                )
                //.........................Spacer
                Spacer(modifier = Modifier.height(10.dp))

            }
        }

    }

    if (openFullDialogCustom.value) {

        Dialog(
            onDismissRequest = {
                openFullDialogCustom.value = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wifi),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),

                    )

                Spacer(modifier = Modifier.height(20.dp))
                //.........................Text: title
                Text(
                    text = "Whoops!!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(8.dp))

                //.........................Text : description
                Text(
                    text = "No Internet connection was found. Check your connection or try again.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                //.........................Spacer
                Spacer(modifier = Modifier.height(24.dp))

                val cornerRadius = 16.dp
                val gradientColor = listOf(Color(0xFFff669f), Color(0xFFff8961))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp),
                    onClick = {
                        if (isOnline(context)) {
                            refreshScreen.value = true
                        }
                    },

                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(cornerRadius)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(colors = gradientColor),
                                shape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
                            )
                            .clip(RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp))
                            /*.background(
                                brush = Brush.linearGradient(colors = gradientColors),
                                shape = RoundedCornerShape(cornerRadius)
                            )*/
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Try Again",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }

            }
        }

    }

    if (refreshScreen.value) {
        refreshScreen.value = false
        openFullDialogCustom.value = false
        WebViewLoader(url, padding = padding)
    }


    //..........................................................................
    //val context = LocalContext.current
    AndroidView(
        modifier = Modifier.padding(padding),
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true

                //webChromeClient
                webChromeClient = WebChromeClient()
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        progressValue = newProgress
                    }
                }

                //webViewClient
                webViewClient = WebViewClient()
                webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        // show dialog
                        if (view != null) {
                            backEnabled = view.canGoBack()
                        }
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        // hide dialog
                        loaderDialogScreen.value = false
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        if (!isOnline(context)) {
                            openFullDialogCustom.value = true
                        }
                    }

                    //Intent to whatsapp
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val waurl = request?.url.toString()
                        if (waurl.startsWith("https://wa.me") || waurl.startsWith("whatsapp://")) {
                            openWhatsApp(waurl, context)
                            return true
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }

                }
                loadUrl(url!!)
                webView = this
            }
        }, update = {
            webView = it
        }
    )
    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }

}

fun openWhatsApp(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    try {
        context.startActivity(intent)
        //true // WhatsApp intent started successfully
    } catch (e: ActivityNotFoundException) {
        // WhatsApp is not installed on the device
        Toast.makeText(context, "Whatsapp not installed", Toast.LENGTH_SHORT).show()
        // WhatsApp intent failed
    }
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // For 29 api or above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
    // For below 29 api
    else {
        @Suppress("DEPRECATION")
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}

//@Composable
//fun ReloadView(url: String, padding: PaddingValues) {
//    Log.v("MainActivity", "Veiw Relaoding.......")
//    WebViewLoader(url = url, padding = padding)
//}
