package cn.xiaolong.lhck.ui.widget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
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
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("******","onCreate");new Thread() {
            @Override
            public void run() {
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(makeFile(UpdateService.this));
                    // 创建连接  
                    URL url = new URL("https://apk.kosungames.com/app-c6-release.apk");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //处理下载读取长度为-1 问题  
                    conn.setRequestProperty("Accept", "application/*");
                    conn.connect();
                    // 获取文件大小  
                    int length = conn.getContentLength();
                    mListener.showProgress(length);
                    InputStream is = conn.getInputStream();
                    int len = 0;
                    byte[] b = new byte[1024];
                    while ((len = is.read(b)) != -1) {
                        fos.write(b, 0, len);
                        progress += len;
                        mListener.onProgress(progress,length);
                    }

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

    public static File makeFile(Context context) {
        File file = new File(context.getExternalCacheDir(), File.separator + "flashloan");
        if (!file.exists()) {
            file.mkdirs();
        }

        File newFile = new File(file, "xiaoniu");
        if (newFile.exists()) {
            newFile.delete();
        }
        return new File(file, "xiaoniu" + ".apk");
    }

    /**
     * 获取temp文件路径名
     *
     * @param context
     * @return
     */
    public static String getTempPath(Context context) {
        File apk = new File(context.getExternalCacheDir(), File.separator + "flashloan" + File.separator + "xiaoniu");
        return apk.getAbsolutePath();
    }
}
