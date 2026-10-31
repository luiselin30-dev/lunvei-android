package com.lunvei.android;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.CookieManager;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends Activity {

    private WebView webView;

    private static final String APP_URL =
        "https://lunvei5-anastasiasalindzer.msk1.amvera.tech/";

           @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(android.graphics.Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(0);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        CookieManager.getInstance().setAcceptCookie(true);

        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    String url) {

                if (url.startsWith(APP_URL)) {
                    return false;
                }

                try {
                    Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url)
                    );
                    startActivity(intent);
                } catch (Exception ignored) {
                }

                return true;
            }
        });

        if (savedInstanceState == null) {
            webView.loadUrl(APP_URL);
        } else {
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
