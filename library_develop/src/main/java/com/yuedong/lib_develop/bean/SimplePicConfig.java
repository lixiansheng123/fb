package com.yuedong.lib_develop.bean;


import com.yuedong.library_develop.R;

/**
 * Created by Administrator on 2015/12/29.
 */
public class SimplePicConfig {
    private int loadPic;
    private int errorPic;

    public SimplePicConfig() {
        loadPic = R.drawable.ic_default1;
        errorPic = R.drawable.ic_default1;
    }

    public int getLoadPic() {
        return loadPic;
    }

    public void setLoadPic(int loadPic) {
        this.loadPic = loadPic;
    }

    public int getErrorPic() {
        return errorPic;
    }

    public void setErrorPic(int errorPic) {
        this.errorPic = errorPic;
    }

}
