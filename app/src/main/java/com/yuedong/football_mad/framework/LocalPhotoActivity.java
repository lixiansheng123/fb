package com.yuedong.football_mad.framework;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.yuedong.football_mad.app.Config;

import java.io.File;

/**
 * 获取本地图片activity
 *
 * @author 俊鹏 on 2016/7/7
 */
public abstract class LocalPhotoActivity extends BaseActivity {
    private String takePath = Config.DIR_PHOTO_TAKE;
    private String cutPath = Config.DIR_PHOTO_CUT;
    private static final int TACK_PIC = 0x001;
    private static final int GET_PIC = 0x002;
    private static final int CUT_PIC = 0x003;
    private String imageName;
    private boolean isCut;// 是否剪裁
    private static final String CUT_SUFFIX = "_cut";

    /**
     * 从相册获取图片
     *
     * @return
     */
    protected void getPhotoByPhotoSet(boolean isCut) {
        this.isCut = isCut;
        imageName = System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_PIC);
    }

    /**
     * 拍照获取图片
     */
    protected void getPhotoByTake(boolean isCut) {
        this.isCut = isCut;
        imageName = System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(takePath, imageName)));
        startActivityForResult(intent, TACK_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TACK_PIC && resultCode == RESULT_OK) {
            if (isCut)
                startPhotoZoom(
                        Uri.fromFile(new File(takePath, imageName)),
                        480);
            else {
                String fullPath = takePath + File.separator + imageName;
                getPicSucceed(fullPath);
            }
        } else if (requestCode == GET_PIC && resultCode == RESULT_OK) {
            Uri uri = data.getData();//普通手机选择后返回的是空
            if (uri != null && uri.toString().contains("content://")) {//小米3手机返回的content://....
                if (isCut)
                    startPhotoZoom(uri, 480);
                else {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri,
                            proj,                 // Which columns to return
                            null,       // WHERE clause; which rows to return (all rows)
                            null,       // WHERE clause selection arguments (none)
                            null);                 // Order-by clause (ascending by name)
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(proj[0]);
                    String picturePath = cursor.getString(columnIndex);
                    getPicSucceed(picturePath);
                }
            } else {
                onActivityResult(CUT_PIC, 0, null);
            }
        } else if (requestCode == CUT_PIC && resultCode == RESULT_OK) {
            String fullPicPath = cutPath + File.separator + imageName;
            getPicSucceed(fullPicPath);
        }
    }


    private void startPhotoZoom(Uri uri1, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri1, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(cutPath, imageName)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CUT_PIC);
    }

    /**
     * 成功获取图片 回调
     */
    protected abstract void getPicSucceed(String path);
}
