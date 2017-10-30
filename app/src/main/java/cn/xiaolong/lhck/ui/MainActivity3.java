package cn.xiaolong.lhck.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.bean.type.TicketTypeEnum;

public class MainActivity3 extends AppCompatActivity {

    FrameLayout frame_layout;
    RelativeLayout line_bottom1, line_bottom2, line_bottom3, line_bottom4;
    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager mManager = getSupportFragmentManager();
    TicketType2Fragment fragment1;
    TicketTypeFragment fragment2;
    MyFollowFragment fragment3;
    MeFragment fragment4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainnew);
        init();
    }


    public void init() {
        initFragments();
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);

        line_bottom1 = (RelativeLayout) findViewById(R.id.line_bottom1);
        line_bottom2 = (RelativeLayout) findViewById(R.id.line_bottom2);
        line_bottom3 = (RelativeLayout) findViewById(R.id.line_bottom3);
//        line_bottom4 = (RelativeLayout) findViewById(R.id.line_bottom4);
        selectPosFragment(0);
        line_bottom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosFragment(0);
            }
        });
        line_bottom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosFragment(1);
            }
        });
        line_bottom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosFragment(2);
            }
        });
        line_bottom4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosFragment(3);
            }
        });
    }

    private void initFragments() {
//        HomeFragment homeFragment = new HomeFragment();
        fragment1 = TicketType2Fragment.getNewInstance(TicketTypeEnum.values()[2]);
        fragment2 = TicketTypeFragment.getNewInstance(TicketTypeEnum.values()[1]);
        fragment3 = MyFollowFragment.getNewInstance(TicketTypeEnum.Follow);
        fragment4 = new MeFragment();
        mFragments.add(fragment2);
        mFragments.add(fragment1);
        mFragments.add(fragment3);
        mFragments.add(fragment4);
        mManager.beginTransaction().add(R.id.frame_layout, fragment2)
                .add(R.id.frame_layout, fragment1)
                .add(R.id.frame_layout, fragment3)
                .add(R.id.frame_layout, fragment4)
                .hide(fragment2)
                .hide(fragment1)
                .hide(fragment3)
                .hide(fragment4)
                .commitAllowingStateLoss();
    }

    public void selectPosFragment(int pos) {
        switch (pos) {
            case 0:
                line_bottom1.setSelected(true);
                line_bottom2.setSelected(false);
                line_bottom3.setSelected(false);
                line_bottom4.setSelected(false);
                break;
            case 1:
                line_bottom1.setSelected(false);
                line_bottom2.setSelected(true);
                line_bottom3.setSelected(false);
                line_bottom4.setSelected(false);
                break;
            case 2:
                line_bottom1.setSelected(false);
                line_bottom2.setSelected(false);
                line_bottom3.setSelected(true);
                line_bottom4.setSelected(false);
                break;
            case 3:
                line_bottom1.setSelected(false);
                line_bottom2.setSelected(false);
                line_bottom3.setSelected(false);
                line_bottom4.setSelected(true);
                break;
        }
        mManager.beginTransaction().hide(fragment2).hide(fragment1)
                .hide(fragment3)
                .hide(fragment4).show(mFragments.get(pos)).commitAllowingStateLoss();
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
}
