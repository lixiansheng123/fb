package com.yuedong.football_mad.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.DataKuSearchAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.SearchRecord;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.db.sqlite.Selector;
import com.yuedong.lib_develop.exception.DbException;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.DbUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.view.SupportScrollConflictListView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/22
 */
public class DataKuSearchFragment extends BaseFragment {
    @ViewInject(R.id.et_search)
    private EditText etSearch;
    @ViewInject(R.id.recordList)
    private SupportScrollConflictListView recordListView;
    private DbUtils dbUtils;
    private DataKuSearchAdapter adapter;
    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dataku_search;
    }

    @Override
    public void ui() {
        dbUtils = DbUtils.create(getActivity());
        adapter = new DataKuSearchAdapter(getActivity());
        recordListView.setAdapter(adapter);
        updateRecordList();


    }

    private void updateRecordList() {
        try {
            List<SearchRecord> all = dbUtils.findAll(Selector.from(SearchRecord.class).where("type", "=", 1).orderBy("time",true).limit(3));
            if (all != null && !all.isEmpty()) {
                adapter.setData(all);
                adapter.notifyDataSetChanged();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @OnClick({R.id.btn_search})
    protected void clickEvent(View view){
        switch (view.getId()){
            case R.id.btn_search:
                String searchContent = etSearch.getText().toString();
                if(TextUtils.isEmpty(searchContent)){
                    T.showShort(getActivity(),"搜索内容不能为空");
                    return;
                }
                try {
                    SearchRecord searchRecord = dbUtils.findFirst(Selector.from(SearchRecord.class).where("content", "=", searchContent));
                    if(searchRecord == null){
                        searchRecord = new SearchRecord();
                        searchRecord.setContent(searchContent);
                        searchRecord.setTime(System.currentTimeMillis());
                        searchRecord.setType(1);
                        dbUtils.save(searchRecord);
                    }else{
                        searchRecord.setTime(System.currentTimeMillis());
                        dbUtils.update(searchRecord);
                    }
                    updateRecordList();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}