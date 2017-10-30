package cn.xiaolong.lhck.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaolong.lhck.R;


/**
 * Created by junbo on 3/11/2016.
 */

public abstract class Base2Activity<T extends ILottery, K extends BasePresenter> extends AppCompatActivity implements BaseView<T> {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    K presenter;
    private SVProgressHUD mSVProgressHUD;
    @Override
    public void fail() {

    }

    @Override
    public void update(List<T> list) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initIntent();
        initDialog();
        initPresenter();
        iniView();

    }

    protected abstract void initIntent();

    protected abstract void initPresenter();

    protected abstract void iniView();

    protected abstract int getLayoutResId();

    public String getResString(int resId) {
        return getResources().getString(resId);
    }

    public String getResString(int resId, Object... args) {
        return getResources().getString(resId, args);
    }

    @Override
    public void onDestroy() {
        dissmissProgressDialog();
        super.onDestroy();
        if (presenter != null) {
            presenter.destory();
        }
    }

    public void showProgressDialog() {
        dissmissProgressDialog();
        mSVProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.Black);
    }


    public void dissmissProgressDialog() {
        if (mSVProgressHUD.isShowing()) {
            mSVProgressHUD.dismiss();
        }
    }
    private void initDialog() {
        mSVProgressHUD = new SVProgressHUD(this);
    }

}
