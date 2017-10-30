package cn.xiaolong.lhck.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import cn.xiaolong.lhck.R;

public class WebNewActivity extends AppCompatActivity {
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        mWebView = (WebView) findViewById(R.id.webview);
        FrameLayout.LayoutParams flp= (FrameLayout.LayoutParams) mWebView.getLayoutParams();
        flp.topMargin= (int) getStatusBarHeight(WebNewActivity.this);
        mWebView.setLayoutParams(flp);

        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("gb2312") ;
        webSettings.setDomStorageEnabled(true);
        mWebView.loadUrl(getIntent().getExtras().getString("url"));
        WebViewClient webViewClient = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                           SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();// 接受所有网站的证书
            }
        };
        mWebView.setWebViewClient(webViewClient);


    }
    long firstTime;

    private double getStatusBarHeight(Context context){
                double statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
                return statusBarHeight;
            }


    //双击退出登录退出app
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1500) {// 如果两次按键时间间隔大于800毫秒，则不退出
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                firstTime = secondTime;// 更新firstTime
                return true;
            } else {
                //借款时切换tab进度变的问题
                finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
