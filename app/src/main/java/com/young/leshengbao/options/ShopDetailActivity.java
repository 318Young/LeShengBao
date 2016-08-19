/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.young.leshengbao.options;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.young.leshengbao.R;
import com.young.leshengbao.adapter.TestAdapter;

public class ShopDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "shop_name";
    public static final String EXTRA_IMAGE = "shop_image";
    private WebView mWebView;
    private WebSettings mWebSetting;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        Intent intent = getIntent();
        final String shopName = intent.getStringExtra(EXTRA_NAME);
        final String shopImage = intent.getStringExtra(EXTRA_IMAGE);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(shopName);

        loadBackdrop(shopImage);

        mWebView = (WebView) findViewById(R.id.wv_detail);
        mWebSetting = mWebView.getSettings();


        //设置支持javascript
        mWebSetting.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://blog.csdn.net/lightyearwp/article/details/5419973");
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
//        lv_detail = (ListView) findViewById(R.id.lv_detail);
//        lv_detail.setAdapter(new TestAdapter(this));
    }

    private void loadBackdrop(String shopImage) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(shopImage).centerCrop().into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
