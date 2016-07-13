package com.yuedong.football_mad.app;

import com.yuedong.lib_develop.app.App;
import com.yuedong.lib_develop.utils.DimenUtils;
import com.yuedong.lib_develop.utils.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/6/3.
 */
public class Config {
    public static int PAGER_SIZE = 12;
    public static final int TITLE_HEIGHT = DimenUtils.dip2px(App.getInstance().getAppContext(),50);
    // 手机号正则
    public static String MOBILE_RULE = "^1\\d{10}$";
    public static final String BASE_CACHE_DIR = FileUtils.getDiskCacheDir(App.getInstance().getAppContext()) + File.separator + "balldata" + File.separator;
    public static final String DIR_PHOTO = BASE_CACHE_DIR + "photo";
    public static final String DIR_PHOTO_TAKE = DIR_PHOTO+File.separator+"take";// 拍照
    public static final String DIR_PHOTO_CUT = DIR_PHOTO+File.separator+"cut";// 裁剪
    public static final String DIR_PHOTO_UPLOAD = DIR_PHOTO+File.separator+"upload";//上传
    static {
        File photoDir = new File(DIR_PHOTO);
        File photoDirTake = new File(DIR_PHOTO_TAKE);
        File photoDirCut = new File(DIR_PHOTO_CUT);
        File photoDirUpload = new File(DIR_PHOTO_UPLOAD);
        if(!photoDir.exists())photoDir.mkdirs();
        if(!photoDirTake.exists())photoDirTake.mkdirs();
        if(!photoDirCut.exists())photoDirCut.mkdirs();
        if(!photoDirUpload.exists())photoDirUpload.mkdirs();
    }

}
