package cn.xiaolong.lhck.ui;

import android.widget.TextView;

import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.base.BaseTitleBar;
import cn.xiaolong.lhck.base.BaseTitleBarActivity;
import cn.xiaolong.lhck.bean.TicketOpenData;
import cn.xiaolong.lhck.bean.TicketRegular;
import cn.xiaolong.lhck.presenter.OpenResultPresenter;
import cn.xiaolong.lhck.presenter.view.IOpenResultView;

/**
 * Created by junbo on 15/11/2016.
 */

public class AboutActivity extends BaseTitleBarActivity<OpenResultPresenter> implements IOpenResultView {
    private TextView tvTitle;
    private TextView tvdes;

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        tvTitle = (TextView) titleBar.center;
        tvTitle.setText("关于我们");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {
        tvdes=(TextView)findViewById(R.id.tvdes);
        tvdes.setText(getResources().getString(R.string.app_name)+"是一款提供多种彩票实时开奖信息的APP。主要功能包括：近期开奖查询、历史开奖查询、关注彩种、趋势分析、号码预测等，为用户提供最新实时开奖信息，方便用户第一时间查看开奖信息，操作简单，是广大彩民的最好的助手。");
    }


    @Override
    protected void setListener() {

    }

    @Override
    public void getSingleOpenResultSuccess(TicketOpenData ticketOpenData) {

    }

    @Override
    public void getRegularSuccess(TicketRegular ticketRegular) {

    }

}
