package cn.xiaolong.lhck.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.xiaolong.lhck.R;

public class MainActivity4 extends AppCompatActivity {


    LinearLayout linemm;
    FrameLayout user;
    FrameLayout more;
    FrameLayout kaijiang;
    FrameLayout yuce;
    FrameLayout ssq;
    FrameLayout buy;
    FrameLayout trend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_index);
        linemm=(LinearLayout)findViewById(R.id.linemm);
        user=(FrameLayout)findViewById(R.id.user);
        kaijiang=(FrameLayout)findViewById(R.id.kaijiang);
        yuce=(FrameLayout)findViewById(R.id.yuce);
        more=(FrameLayout)findViewById(R.id.more);
        ssq=(FrameLayout)findViewById(R.id.ssq);
        buy=(FrameLayout)findViewById(R.id.buy);
        trend=(FrameLayout)findViewById(R.id.trend);

//        LinearLayout.LayoutParams flp= (LinearLayout.LayoutParams) linemm.getLayoutParams();
//        flp.topMargin= (int) getStatusBarHeight(MainActivity4.this);
//        linemm.setLayoutParams(flp);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,FollowActivity.class));
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,MoreActivity.class));
            }
        });
        kaijiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,ActivityFragment1.class));
            }
        });
        yuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,ActivityFragment2.class));
            }
        });
        ssq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,ActivityFragment3.class));
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,ActivityFragment4.class));
            }
        });
        trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity4.this,RuleActivity.class));
            }
        });

    }



    long firstTime;

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //将这一行注释掉，阻止activity保存fragment的状态,导致activity被回收后fragment重叠问题
//        super.onSaveInstanceState(outState);
    }
    private double getStatusBarHeight(Context context){
        double statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
        return statusBarHeight;
    }
}
