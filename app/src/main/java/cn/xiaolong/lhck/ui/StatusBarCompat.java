package cn.xiaolong.lhck.ui;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Utils for status bar
 * Created by qiu on 3/29/16.
 */
public class StatusBarCompat {

    //Get alpha color
    static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * set statusBarColor
     *
     * @param statusColor color
     * @param alpha       0 - 255
     */
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, int alpha) {
        setStatusBarColor(activity, calculateStatusBarColor(statusColor, alpha));
    }

    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarColor(activity, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarColor(activity, statusColor);
        }
    }

    /**
     * 设置statusbar为渐变色背景
     *
     * @param activity
     * @param drawableResource 渐变背景drawableResource
     */
    public static void setStatusBarGradientColor(@NonNull Activity activity, int drawableResource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarGradientColor(activity, drawableResource, false);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarGradientColor(activity, drawableResource);
        }
    }

    public static void translucentStatusBar(@NonNull Activity activity) {
        translucentStatusBar(activity, false);
    }

    /**
     * 设置statusbar头部
     *
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBarForImage(@NonNull Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBarForImage(activity, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBarForImage(activity);
        }
    }

    /**
     * change to full screen mode
     *
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity);
        }
    }

    /**
     * @param activity
     * @param hideStatusBarBackground statusBar 是否透明
     * @param bgView                  需要沉浸效果的view
     */
    public static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBackground, View bgView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBackground, bgView);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity, bgView);
        }
    }

    public static void translucentStatusBarForFragment(@NonNull Fragment fragment, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBarForFragment(fragment, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBarForFragment(fragment);
        }
    }

    /**
     * @param fragment
     * @param hideStatusBarBackground statusBar 是否透明
     * @param bgView                  需要沉浸效果的view
     */
    public static void translucentStatusBarForFragment(@NonNull Fragment fragment, boolean hideStatusBarBackground, View bgView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBarForFragment(fragment, hideStatusBarBackground, bgView);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBarForFragment(fragment, bgView);
        }
    }

    public static void setStatusBarColorForCollapsingToolbar(@NonNull Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                             Toolbar toolbar, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        }
    }
}
