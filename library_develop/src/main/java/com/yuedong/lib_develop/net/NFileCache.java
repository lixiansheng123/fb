package com.yuedong.lib_develop.net;

import android.text.TextUtils;

import com.yuedong.lib_develop.app.App;
import com.yuedong.lib_develop.net.NCache;
import com.yuedong.lib_develop.utils.FileUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/6/2.
 */
public class NFileCache extends NCache {
    private static String dir = FileUtils.getDiskCacheDir(App.getInstance().getAppContext()) + "/cache/data";
    private static File cacheDir;

    static {
        cacheDir = new File(dir);
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        L.d("缓存文件目录" + dir.toString());
    }

    @Override
    public boolean cacheData(String key, String json) {
        checkCache();
        if (!android.text.TextUtils.isEmpty(json) && !TextUtils.isEmpty(key)) {
            try {
                FileOutputStream fos = new FileOutputStream(new File(cacheDir, key));
                fos.write(json.getBytes());
                fos.flush();
                fos.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String getData(String key) {
        checkCache();
        File read = new File(cacheDir, key);
        if (read.exists()) {
            try {
                FileInputStream fis = new FileInputStream(read);
                String json = StreamUtils.conversionInputStreamToString(fis);
                L.d("缓存json数据======>>" + json);
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void checkCache() {
        if (cacheDir != null) {
            for (File file : cacheDir.listFiles()) {
                if (System.currentTimeMillis() - (file.lastModified() + cacheTime) >= 0) {
                    L.d("删除缓存");
                    file.delete();
                }
            }
        }
    }
}
