package cn.xiaolong.lhck.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.base.BaseTitleBar;

/**
 * Created by Admin on 2017/9/4.
 */

public class MeFragment extends Fragment {

    View mView;
    Switch wifi;
    TextView rule;
    TextView about;
    TextView checkVersion;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_me, null);
        wifi = (Switch) mView.findViewById(R.id.switch_wifi);
        rule = (TextView) mView.findViewById(R.id.lotteryRule);
        about = (TextView) mView.findViewById(R.id.about);
        checkVersion = (TextView) mView.findViewById(R.id.checkVersion);
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);



        BaseTitleBar mTitleBar = new BaseTitleBar(getContext(), toolbar, getDefaultTitleBarLayout());
        mTitleBar.setOnLeftClickListener(view -> onTitleLeftClick());
//        initTitleBar(mTitleBar);
        TextView tvTitle = (TextView) mTitleBar.center;
        View view = mTitleBar.left;
        view.setVisibility(View.GONE);
        tvTitle.setText("更多");
        tvTitle.setTextSize(18);
        View viewn = (View) mTitleBar.viewn;
        if(Build.VERSION.SDK_INT > 19) {
            // TODO
            viewn.setVisibility(View.GONE);
        }else{
            viewn.setVisibility(View.VISIBLE);
        }

        boolean savedWifi = LotteryUtils.getBooleanFromSharePreferences(getActivity(), Constants.Setting.WIFI_KEY, false);
        wifi.setChecked(savedWifi);
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LotteryUtils.saveBooleanToSharePreferences(getActivity(), Constants.Setting.WIFI_KEY, wifi.isChecked());
            }
        });
        rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),RuleActivity.class));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AboutActivity.class));
            }
        });
        checkVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"没有检测到最新版本！",Toast.LENGTH_SHORT).show();
                    }
                },1000);
            }
        });

        return mView;
    }


    public int getDefaultTitleBarLayout() {
        return R.layout.titlebar_normalf;
    }
    public void onTitleLeftClick() {

    }

    public  int dp2px(Context context, int dip) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
