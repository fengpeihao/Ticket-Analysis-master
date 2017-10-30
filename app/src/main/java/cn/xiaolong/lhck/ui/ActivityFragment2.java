package cn.xiaolong.lhck.ui;

import android.content.Context;
import android.widget.TextView;

import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.base.BaseTitleBar;
import cn.xiaolong.lhck.base.BaseTitleBarActivity;

public class ActivityFragment2 extends BaseTitleBarActivity {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment2;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

    }


    long firstTime;

    private double getStatusBarHeight(Context context){
        double statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
        return statusBarHeight;
    }

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        TextView tvTitle = (TextView) titleBar.center;
        tvTitle.setText("全国彩讯");
    }
}
