package cn.xiaolong.lhck.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.adapter.TabFragmentAdapter;
import cn.xiaolong.lhck.base.BaseFuncActivity;
import cn.xiaolong.lhck.bean.type.TicketTypeEnum;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/7 14:45
 */

public class MainActivity extends BaseFuncActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initTabTop(getFragmentTitle(), getFragmentList());

    }

    private List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MyFollowFragment.getNewInstance(TicketTypeEnum.Follow));
        for (int i = 1; i < TicketTypeEnum.values().length; i++) {
            fragmentList.add(TicketTypeFragment.getNewInstance(TicketTypeEnum.values()[i]));
        }
        return fragmentList;
    }

    private List<String> getFragmentTitle() {
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < TicketTypeEnum.values().length; i++) {
            titles.add(TicketTypeEnum.values()[i].getValue());
        }
        return titles;
    }

    private void initTabTop(List<String> fragmentTitles, List<Fragment> fragmentList) {
        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(fragmentList, fragmentTitles, getSupportFragmentManager(), this);
        ViewPager pagerContent = findView(R.id.pageContent);
        pagerContent.setAdapter(tabFragmentAdapter);
        TabLayout tlMainTop = findView(R.id.tlMainTop);
        tlMainTop.setupWithViewPager(pagerContent);
    }

    @Override
    protected void setListener() {

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
}
