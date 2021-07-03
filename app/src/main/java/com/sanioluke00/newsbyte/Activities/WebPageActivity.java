package com.sanioluke00.newsbyte.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sanioluke00.newsbyte.R;

public class WebPageActivity extends AppCompatActivity {

    ImageButton news_webpage_back_btn;
    TextView news_webpage_title;
    WebView news_webpage_wv;

    String newstitle, newsurl;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        news_webpage_back_btn = findViewById(R.id.news_webpage_back_btn);
        news_webpage_title = findViewById(R.id.news_webpage_title);
        news_webpage_wv = findViewById(R.id.news_webpage_wv);

        Intent intent = getIntent();
        newstitle = intent.getStringExtra("newstitle");
        newsurl = intent.getStringExtra("newsURL");

        news_webpage_wv.zoomIn();
        news_webpage_wv.zoomOut();

        news_webpage_wv.setWebViewClient(new WebViewClient());
        news_webpage_wv.getSettings().setJavaScriptEnabled(true);
        news_webpage_wv.getSettings().setDomStorageEnabled(true);
        news_webpage_title.setText(newstitle);
        news_webpage_wv.loadUrl(newsurl);

        news_webpage_back_btn.setOnClickListener(v -> finish());
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}