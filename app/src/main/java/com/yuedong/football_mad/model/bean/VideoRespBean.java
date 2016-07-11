package com.yuedong.football_mad.model.bean;

import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/11
 */
public class VideoRespBean extends ListResponse<VideoBean> {
    private Data data;

    @Override
    public List<VideoBean> getDataList() {
        return getData().getList();
    }

    @Override
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends ListResponse.Data{
        private List<VideoBean> list;

        public List<VideoBean> getList() {
            return list;
        }

        public void setList(List<VideoBean> list) {
            this.list = list;
        }
    }
}
