package com.example.prakt_11_14;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WebView extends AppCompatActivity {
    private android.webkit.WebView webView;

    private EditText urlInput;

    private String home = "https://ya.ru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        urlInput = (EditText) findViewById(R.id.editTextText);

        webView = (android.webkit.WebView) findViewById(R.id.web_view);

        webView.loadUrl(home);
        urlInput.setText(home);

        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        urlInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                String url = urlInput.getText().toString();

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://ya.ru/search/?text=" + url;
                }

                webView.loadUrl(url); // Загружаем введённый URL
                return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        // Если есть история в WebView, возвращаемся назад
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}