package com.imsle.cqceteasayschool.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.imsle.cqceteasayschool.R;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends QMUIActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.webview)
    WebView webView  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_web_view,null);
        ButterKnife.bind(this, root);
        setContentView(root);
        initTopBar();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String url = getIntent().getExtras().getString("url");
            initWebView(url);
        }else{

        }
    }

    private void initWebView(String url){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                String title = "正在加载.....";
                mTopBar.setTitle(title);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                mTopBar.setTitle(title);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

            }
        });
        webView.loadUrl(url);
    }

    public void initTopBar(){

        mTopBar.addLeftImageButton(R.mipmap.left,R.id.topbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
                QMUIStatusBarHelper.translucent(getWindow(),getResources().getColor(R.color.myFragmentTopBackColor));
            }
        });
        mTopBar.setTitle(R.string.about_title);
        //改为浅色
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        mTopBar.setBackgroundColor(getResources().getColor(R.color.homeToolBarColor));


    }
}
