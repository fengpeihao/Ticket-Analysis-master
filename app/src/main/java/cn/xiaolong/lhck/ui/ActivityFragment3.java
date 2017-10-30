package cn.xiaolong.lhck.ui;

import android.content.Context;
import android.widget.TextView;

import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.base.BaseTitleBar;
import cn.xiaolong.lhck.base.BaseTitleBarActivity;
import cn.xiaolong.lhck.bean.type.TicketTypeEnum;

public class ActivityFragment3 extends BaseTitleBarActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment1;
    }

    @Override
    protected void init() {
        TicketTypeNew2Fragment fragment1 = TicketTypeNew2Fragment.getNewInstance(TicketTypeEnum.values()[4]);
        getSupportFragmentManager().beginTransaction().add(R.id.frame,fragment1).commitAllowingStateLoss();;
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
        tvTitle.setText("时时彩高频");
    }
}
