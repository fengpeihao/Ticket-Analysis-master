package cn.xiaolong.lhck.ui;

/**
 * Created by junbo on 16/11/2016.
 */

public interface BasePresenter<T> {
    public void attach(T view);

    public void start();

    public void destory();

}