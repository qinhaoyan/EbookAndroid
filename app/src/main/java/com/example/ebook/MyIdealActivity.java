package com.example.ebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ebook.Util.Util;
import com.example.ebook.view.MyWebView;

public class MyIdealActivity extends AppCompatActivity {
    private String userid;
    private MyWebView web_view;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.initWindow(getWindow());
        setContentView(R.layout.activity_my_ideal);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userid = bundle.getString("userid");
        ImageView goback = findViewById(R.id.img_myideal_goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        web_view = findViewById(R.id.wv_myideal);
        Util.initView(web_view);
        web_view.setWebViewClient(new WebViewClient());
        web_view.addJavascriptInterface(new javaToJS(),"javaToJS");
        web_view.loadUrl(getResources().getString(R.string.url)+"/myideal");
        web_view.setOnScrollChangeListener(new MyWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                web_view.evaluateJavascript("javascript:loadcontent()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {}
                });
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {

            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
            }
        });
    }
    public class javaToJS{
        @JavascriptInterface
        public String getUserid(){
            return userid;
        }
        @JavascriptInterface
        public void showToast(String msg){
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void toContent(String idealid){
            Intent intent = new Intent(MyIdealActivity.this, IdealContentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("idealid",idealid);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
