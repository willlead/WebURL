package com.example.weburl;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout linear;
    Button btnGo;
    EditText edtURL;
    WebView webView;
    private InputMethodManager imm;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        bar.hide();

        linear = (LinearLayout)findViewById(R.id.linear);
        btnGo = (Button)findViewById(R.id.btnGo);
        edtURL = (EditText)findViewById(R.id.edtURL);
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getOn

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl("http://funnywork.co.kr");

        edtURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtURL.setSelection(edtURL.getText().length());
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edtURL.getText().toString();
                webView.loadUrl(url);
                edtURL.setText("http://");
                linear.setVisibility(View.GONE);
                imm.hideSoftInputFromWindow(edtURL.getWindowToken(), 0);
            }
        });

        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {

            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                final int X,Y, oldX, oldY;
                X = scrollX;
                Y = scrollY;
                oldX = oldScrollX;
                oldY = oldScrollY;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int offset = webView.getHeight()/100;

                        Log.i("web", ""+Y);
                        //if(Y > webView.getTop() + offset)
                        if(Y == webView.getTop())
                        {
                            setVisibleLinear(true);
                        }else {
                            setVisibleLinear(false);
                        }
                    }
                }).start();


            }
        });
    }

    public void setVisibleLinear(final boolean isVisible){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isVisible){
                    linear.setVisibility(View.VISIBLE);
                } else {
                    linear.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                Toast.makeText(this, "다운", Toast.LENGTH_SHORT).show();
//                linear.setVisibility(View.GONE);


                break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(this, "업", Toast.LENGTH_SHORT).show();/**/
//                linear.setVisibility(View.VISIBLE);
//                imm.hideSoftInputFromWindow(edtURL.getWindowToken(), 0);
                break;
            default:
//                Toast.makeText(this, "터치", Toast.LENGTH_SHORT).show();
        }
        return super.onTouchEvent(event);
    }




}
