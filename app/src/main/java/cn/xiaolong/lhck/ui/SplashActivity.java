package cn.xiaolong.lhck.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.xiaolong.lhck.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ac_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                loginByGet();
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(SplashActivity.this,"网络错误请重新登录",Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    final Map<String, Object> map = parseJsonMap((String) msg.obj);
                    if (map.get("rt_code") != null && map.get("rt_code").toString().equals("200")) {
                        Map<String, Object> mapnew = (Map<String, Object>) map.get("data");
                        if (mapnew.get("show_url").toString().equals("1")) {
                            final boolean isfirst = SPUtil.getFirstIn(SplashActivity.this, "isfirst", true);
//                            if (isfirst) {
//                                Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);
//                                intent.putExtra("url", "" + mapnew.get("url").toString());
//                                startActivity(intent);
//                                SPUtil.setFirstIn(SplashActivity.this, "isfirst", false);
//                                finish();
//                            } else {
                                Intent intent = new Intent(SplashActivity.this, WebNewActivity.class);
                                intent.putExtra("url", "" + mapnew.get("url").toString());
                                startActivity(intent);
                                finish();
//                            }

                        } else {
                            init();
                        }
                    } else {
                        init();
                    }
                    break;
            }
        }
    };

    public void init(){
        startActivity(new Intent(SplashActivity.this, DesignMainActivity.class));
        finish();
    }
    public void loginByGet() {

        try {

            URL url = new URL("http://app.412988.com/Lottery_server/check_and_get_url.php?type=android&appid=201610171106");
            // url.openConnection()打开网络链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(15000);// 设置超时的时间
            urlConnection.setConnectTimeout(15000);// 设置链接超时的时间
            // 设置请求的头
            urlConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            // 获取响应的状态码 404 200 505 302
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    os.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                os.close();
                // 返回字符串
                String result = new String(os.toByteArray());
                System.out.println("***************" + result
                        + "******************");
                Message msg = new Message();
                msg.what = 0;
                msg.obj = result;
                handler.sendMessage(msg);
            } else {
                System.out.println("------------------链接失败-----------------");
            }

        } catch (Exception e) {
//            e.printStackTrace();

            handler.sendEmptyMessage(1);
        }
    }


    public Map<String, Object> parseJsonMap(String jsonStr) {
        try {
            return JSON.parseObject(jsonStr,
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (Exception e) {
            return new HashMap<String, Object>();
        }
    }

}
