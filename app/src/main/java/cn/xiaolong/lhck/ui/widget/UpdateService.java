package cn.xiaolong.lhck.ui.widget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fengpeihao on 2017/10/30.
 */

public class UpdateService extends Service {

    private OnProgressListener mListener;
    private int progress;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mListener.showProgress(msg.arg1);
                    break;
                case 2:
                    mListener.onProgress(progress, msg.arg1);
                    break;
                case 3:
                    mListener.install(new File(getTempPath(UpdateService.this)));
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public UpdateService getService(OnProgressListener listener) {
            mListener = listener;
            return UpdateService.this;
        }

        public void startDown() {
            Log.e("******", "onCreate");
            new Thread() {
                @Override
                public void run() {
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(makeFile(UpdateService.this));
                        // 创建连接  
                        URL url = new URL("http://bmob-cdn-13110.b0.upaiyun.com/2017/09/21/42c84147401a93fe80ffedff581bab06.apk");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        //处理下载读取长度为-1 问题  
                        conn.setRequestProperty("Accept", "application/*");
                        conn.connect();
                        // 获取文件大小  
                        int length = conn.getContentLength();
                        Message msg = new Message();
                        msg.what = 1;
                        msg.arg1 = length;
                        handler.sendMessage(msg);
                        InputStream is = conn.getInputStream();
                        int len = 0;
                        byte[] b = new byte[1024];
                        while ((len = is.read(b)) != -1) {
                            fos.write(b, 0, len);
                            progress += len;
                            Message msg2 = new Message();
                            msg2.what = 2;
                            msg2.arg1 = length;
                            handler.sendMessage(msg2);
                        }
                        fos.flush();
                        handler.sendEmptyMessage(3);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public File makeFile(Context context) {
        File file = new File(context.getExternalCacheDir(), File.separator + "flashloan11");
        if (!file.exists()) {
            file.mkdirs();
        }

        File newFile = new File(file, "xiaoniu22");
        if (newFile.exists()) {
            newFile.delete();
        }
        return new File(file, "xiaoniu22" + ".apk");
    }

    /**
     * 获取temp文件路径名
     *
     * @param context
     * @return
     */
    public String getTempPath(Context context) {
        File apk = new File(context.getExternalCacheDir(), File.separator + "flashloan11" + File.separator + "xiaoniu22"+".apk");
        return apk.getAbsolutePath();
    }
}
