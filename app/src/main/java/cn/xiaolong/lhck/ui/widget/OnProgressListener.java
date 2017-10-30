package cn.xiaolong.lhck.ui.widget;

/**
 * Created by fengpeihao on 2017/10/30.
 */

public interface OnProgressListener {
    void showProgress(int max);

    void onProgress(int progress,int max);
}
