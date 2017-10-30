package cn.xiaolong.lhck.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;

import cn.xiaolong.lhck.R;


public class NavigationActivity extends Activity {
    private Bitmap[] bitmap;



    public void initView() {
        bitmap = new Bitmap[3];
        bitmap[0] = getImage("navigation_1.png");
        bitmap[1] = getImage("navigation_2.png");
        bitmap[2] = getImage("navigation_3.png");


        ArrayList<View> views = new ArrayList<View>();

        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_navigation_viewpager);
        viewPager.setBackgroundColor(0xff000000);
        /**
         * 加入导览图集
         */
        for (int i = 0; i < bitmap.length; i++) {
            View v = getLayoutInflater().inflate(R.layout.activity_navigation_item_view, null);
            ImageView image = (ImageView) v.findViewById(R.id.image);
            if (i == (bitmap.length - 1)) {
                ImageView mb = (ImageView) v.findViewById(R.id.image);
                mb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(NavigationActivity.this, WebNewActivity.class);
                        intent.putExtra("url", "" + getIntent().getExtras().getString("url"));
                        startActivity(intent);
                        finish();

                    }
                });
            }

            image.setImageBitmap(bitmap[i]);
            views.add(v);
        }

        /**
         * 初始化dot
         */


        NavigationAdapter navigationAdapter = new NavigationAdapter(views);
        viewPager.setAdapter(navigationAdapter);
        viewPager.setBackgroundColor(Color.TRANSPARENT);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int scroll_state) {
            }
        });

    }




    private Bitmap getImage(String fileName) {
        try {
            BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            //下面这个设置是将图片边界不可调节变为可调节
            bitmapFactoryOptions.inJustDecodeBounds = true;
            bitmapFactoryOptions.inSampleSize = 2;

            int yRatio = (int) Math.ceil(bitmapFactoryOptions.outHeight / 150);
            int xRatio = (int) Math.ceil(bitmapFactoryOptions.outWidth / 150);

            if (yRatio < 1 || xRatio > 1) {
                if (yRatio > xRatio) {
                    bitmapFactoryOptions.inSampleSize = yRatio;
                } else {
                    bitmapFactoryOptions.inSampleSize = xRatio;
                }
            }

            bitmapFactoryOptions.inJustDecodeBounds = false;

            InputStream is = getAssets().open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, bitmapFactoryOptions);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private class NavigationAdapter extends PagerAdapter {
        private ArrayList<View> views;

        public NavigationAdapter(ArrayList<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarCompat.translucentStatusBarForImage(NavigationActivity.this, true);
        setContentView(R.layout.activity_navigation_layout);
        initView();
    }
}
