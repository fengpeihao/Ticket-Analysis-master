package cn.xiaolong.lhck.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.bean.type.TicketTypeEnum;
import cn.xiaolong.lhck.ui.widget.OnProgressListener;
import cn.xiaolong.lhck.ui.widget.UpdateService;

public class DesignMainActivity extends AppCompatActivity implements OnProgressListener {

    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tv1s)
    TextView mTv1s;
    @BindView(R.id.iv_bottom1)
    ImageView mIvBottom1;
    @BindView(R.id.line_bottom1)
    RelativeLayout mLineBottom1;
    @BindView(R.id.tv2s)
    TextView mTv2s;
    @BindView(R.id.iv_bottom2)
    ImageView mIvBottom2;
    @BindView(R.id.line_bottom2)
    RelativeLayout mLineBottom2;
    @BindView(R.id.tv3s)
    TextView mTv3s;
    @BindView(R.id.iv_bottom3)
    ImageView mIvBottom3;
    @BindView(R.id.line_bottom3)
    RelativeLayout mLineBottom3;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //    @BindView(R.id.tv4s)
//    TextView mTv4s;
//    @BindView(R.id.iv_bottom4)
//    ImageView mIvBottom4;
//    @BindView(R.id.line_bottom4)
//    RelativeLayout mLineBottom4;
    //    @BindView(R.id.tv_home)
//    TextView mTvHome;
//    @BindView(R.id.tv_kj)
//    TextView mTvKj;
    private Unbinder mBind;
    private FragmentManager mFM;
    private List<Fragment> mFragments = new ArrayList<>();
    private TicketType2Fragment fragment1;
    private TicketTypeFragment fragment2;
    private TicketTypeNew2Fragment fragment3;
    private MeFragment fragment4;
    private UpdateService.MyBinder mMyBinder;
    private UpdateService mUpdateService;
    private MyServiceConnected mMyServiceConnected;
    private ProgressBar mProgressBar;
    private TextView mTv_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_main);
        mBind = ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));//设置标题颜色
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
//        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                mAnimationDrawable.stop();
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                mAnimationDrawable.start();
//            }
//        };
//        mDrawerToggle.syncState();
//        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setNavigationViewItemClickListener();
        initFragments();
//        init();
        mMyServiceConnected = new MyServiceConnected();
        showUpdateDialog();
    }

    //    private void init() {
//        mFM = getSupportFragmentManager();
//        fragment1 = TicketTypeFragment.getNewInstance(TicketTypeEnum.values()[1]);
//        fragment2 = TicketType2Fragment.getNewInstance(TicketTypeEnum.values()[2]);
//        mFM.beginTransaction().add(R.id.frame_layout, fragment1).add(R.id.frame_layout, fragment2).show(fragment1).hide(fragment2).commit();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.line_bottom1, R.id.line_bottom2, R.id.line_bottom3/*, R.id.line_bottom4*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_bottom1:
                selectPosFragment(0);
                break;
            case R.id.line_bottom2:
                selectPosFragment(1);
                break;
            case R.id.line_bottom3:
                selectPosFragment(2);
                break;
//            case R.id.line_bottom4:
//                selectPosFragment(3);
//                break;
        }
    }

    private void initFragments() {
//        HomeFragment homeFragment = new HomeFragment();
        mFM = getSupportFragmentManager();
        fragment1 = TicketType2Fragment.getNewInstance(TicketTypeEnum.values()[2]);
        fragment2 = TicketTypeFragment.getNewInstance(TicketTypeEnum.values()[1]);
        fragment3 = TicketTypeNew2Fragment.getNewInstance(TicketTypeEnum.values()[3]);
//        fragment4 = new MeFragment();
        mFragments.add(fragment2);
        mFragments.add(fragment1);
        mFragments.add(fragment3);
//        mFragments.add(fragment4);
        mFM.beginTransaction().add(R.id.frame_layout, fragment2)
                .add(R.id.frame_layout, fragment1)
                .add(R.id.frame_layout, fragment3)
//                .add(R.id.frame_layout, fragment4)
                .hide(fragment2)
                .hide(fragment1)
                .hide(fragment3)
//                .hide(fragment4)
                .commitAllowingStateLoss();
        selectPosFragment(0);
    }

    public void selectPosFragment(int pos) {
        switch (pos) {
            case 0:
                mLineBottom1.setSelected(true);
                mLineBottom2.setSelected(false);
                mLineBottom3.setSelected(false);
//                mLineBottom4.setSelected(false);
                break;
            case 1:
                mLineBottom1.setSelected(false);
                mLineBottom2.setSelected(true);
                mLineBottom3.setSelected(false);
//                mLineBottom4.setSelected(false);
                break;
            case 2:
                mLineBottom1.setSelected(false);
                mLineBottom2.setSelected(false);
                mLineBottom3.setSelected(true);
//                mLineBottom4.setSelected(false);
                break;
            case 3:
                mLineBottom1.setSelected(false);
                mLineBottom2.setSelected(false);
                mLineBottom3.setSelected(false);
//                mLineBottom4.setSelected(true);
                break;
        }
        mFM.beginTransaction().hide(fragment2).hide(fragment1)
                .hide(fragment3)
                /*.hide(fragment4)*/.show(mFragments.get(pos)).commitAllowingStateLoss();
    }

//    @OnClick(R.id.tv_home)
//    public void onMTvHomeClicked() {
//        mTvHome.setBackgroundResource(R.color.color_249B23);
//        mTvHome.setTextColor(getResources().getColor(R.color.white));
//        mTvKj.setBackgroundResource(R.color.white);
//        mTvKj.setTextColor(getResources().getColor(R.color.color_249B23));
//        mFM.beginTransaction().show(fragment1).hide(fragment2).commit();
//    }
//
//    @OnClick(R.id.tv_kj)
//    public void onMTvKjClicked() {
//        mTvHome.setBackgroundResource(R.color.white);
//        mTvHome.setTextColor(getResources().getColor(R.color.color_249B23));
//        mTvKj.setBackgroundResource(R.color.color_249B23);
//        mTvKj.setTextColor(getResources().getColor(R.color.white));
//        mFM.beginTransaction().show(fragment2).hide(fragment1).commit();
//    }

    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_attention:
                        startActivity(new Intent(DesignMainActivity.this, FollowActivity.class));
                        break;
                    case R.id.item_rule:
                        startActivity(new Intent(DesignMainActivity.this, RuleActivity.class));
                        break;
                    case R.id.item_more:
                        startActivity(new Intent(DesignMainActivity.this, MoreActivity.class));
                        break;
                    case R.id.item_about:
                        startActivity(new Intent(DesignMainActivity.this, AboutActivity.class));
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
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

    private void showUpdateDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("更新").setIcon(R.mipmap.ic_launcher).setMessage("发现新版本，请更新")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setNeutralButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DesignMainActivity.this, UpdateService.class);
                        bindService(intent, mMyServiceConnected, Context.BIND_AUTO_CREATE);
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void showProgress(int max) {
        showProgressDialog();
    }

    @Override
    public void onProgress(int progress, int max) {
        if (mProgressBar == null) {
            showUpdateDialog();
        }
        mProgressBar.setProgress(progress * 100 / max);
        mTv_progress.setText("下载中(" + progress * 100 / max + "%)");
    }

    private void showProgressDialog() {
        Dialog dialog = new Dialog(this);
        View view = View.inflate(this, R.layout.dialog_update_progress, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        mTv_progress = (TextView) view.findViewById(R.id.tv_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    class MyServiceConnected implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (UpdateService.MyBinder) service;
            mUpdateService = mMyBinder.getService(DesignMainActivity.this);
            mMyBinder.startDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //将这一行注释掉，阻止activity保存fragment的状态,导致activity被回收后fragment重叠问题
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        unbindService(mMyServiceConnected);
    }

    @Override
    public void install(File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT < 24) {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            } else {
                Uri uri = FileProvider.getUriForFile(this, this.getPackageName() + ".updatefileprovider", file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
