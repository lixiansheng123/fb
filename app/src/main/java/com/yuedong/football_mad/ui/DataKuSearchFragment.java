package com.yuedong.football_mad.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.DataKuSearchAdapter;
import com.yuedong.football_mad.adapter.SearchAllAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.FinalSearchAllBean;
import com.yuedong.football_mad.model.bean.SearchAllBean;
import com.yuedong.football_mad.model.bean.SearchAllRespBean;
import com.yuedong.football_mad.model.bean.SearchRecord;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.db.sqlite.Selector;
import com.yuedong.lib_develop.exception.DbException;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.ioc.annotation.event.OnItemClick;
import com.yuedong.lib_develop.utils.DbUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.view.SupportScrollConflictListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/6/22
 */
public class DataKuSearchFragment extends BaseFragment {
    @ViewInject(R.id.et_search)
    private EditText etSearch;
    @ViewInject(R.id.recordList)
    private SupportScrollConflictListView recordListView;
    @ViewInject(R.id.listview)
    private ListView listView;
    private DbUtils dbUtils;
    private DataKuSearchAdapter adapter;
    private String searchTask;
    private SearchAllAdapter searchAllAdapter;
    private List<SearchRecord> recordData;
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
        searchAllAdapter = new SearchAllAdapter(getActivity());
        listView.setAdapter(searchAllAdapter);
        dbUtils = DbUtils.create(getActivity());
        adapter = new DataKuSearchAdapter(getActivity());
        recordListView.setAdapter(adapter);
        updateRecordList();


    }

    private void updateRecordList() {
        try {
            recordData = dbUtils.findAll(Selector.from(SearchRecord.class).where("type", "=", 1).orderBy("time",true).limit(3));
            if (recordData != null && !recordData.isEmpty()) {
                adapter.setData(recordData);
                adapter.notifyDataSetChanged();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        insertSearchKeyToLocal(searchContent);
        if(tag.equals(searchTask)){
            SearchAllRespBean bean = (SearchAllRespBean) data;
            SearchAllRespBean.List list = bean.getData().getList();
            List<FinalSearchAllBean> all = new ArrayList<>();
            List<FinalSearchAllBean> finalAthlete = new ArrayList<>();
            List<FinalSearchAllBean> finalCountry = new ArrayList<>();
            List<FinalSearchAllBean> finalGame = new ArrayList<>();
            List<FinalSearchAllBean> finalOther = new ArrayList<>();
            List<FinalSearchAllBean> finalTeam = new ArrayList<>();
            for (SearchAllBean a:list.getAthlete()){
                FinalSearchAllBean fa = new FinalSearchAllBean();
                toBean(fa,a,3);
                finalAthlete.add(fa);
            }
            for(SearchAllBean b:list.getCountry()){
                FinalSearchAllBean fa = new FinalSearchAllBean();
                toBean(fa,b,4);
                finalCountry.add(fa);
            }
            for(SearchAllBean c:list.getGame()){
                FinalSearchAllBean fa = new FinalSearchAllBean();
                toBean(fa,c,1);
                finalGame.add(fa);
            }
            for(SearchAllBean d:list.getOther()){
                FinalSearchAllBean fa = new FinalSearchAllBean();
                toBean(fa,d,5);
                finalOther.add(fa);
            }
            for(SearchAllBean e:list.getTeam()){
                FinalSearchAllBean fa = new FinalSearchAllBean();
                toBean(fa,e,2);
                finalTeam.add(fa);
            }
            all.addAll(finalAthlete);
            all.addAll(finalCountry);
            all.addAll(finalGame);
            all.addAll(finalOther);
            all.addAll(finalTeam);
            if(all.isEmpty()){
                T.showShort(getActivity(),"未能搜索到内容");
                searchAllAdapter.setEmpty();
                return;
            }
            searchAllAdapter.setData(all);
        }
    }
    private void toBean(FinalSearchAllBean fa,SearchAllBean b,int type){
        fa.setName(b.getName());
        fa.setId(b.getId());
        fa.setLogo(b.getLogo());
        fa.setType(type);
    }
    String searchContent;
    @OnClick({R.id.btn_search})
    protected void clickEvent(View view){
        switch (view.getId()){
            case R.id.btn_search:
                searchContent  = etSearch.getText().toString();
                if(TextUtils.isEmpty(searchContent)){
                    T.showShort(getActivity(),"搜索内容不能为空");
                    return;
                }
                search();
                break;
        }
    }

    /**
     * 搜索
     */
    private void search(){
        Map<String,String> post = new HashMap<>();
        post.put("searchkey",searchContent);
        searchTask = RequestHelper.post(Constant.URL_SEARCH_ALL,post, SearchAllRespBean.class,false,false,this);
    }

    /**
     * 保存搜索关键字到本地
     * @param searchContent
     */
    private void insertSearchKeyToLocal(String searchContent){
        SearchRecord searchRecord = null;
        try {
            searchRecord = dbUtils.findFirst(Selector.from(SearchRecord.class).where("content", "=", searchContent));
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
    }
    @OnItemClick(value = R.id.recordList)
    protected void recordItemClickListener(AdapterView<?> parent, View view, int position, long id){
        searchContent = recordData.get(position).getContent();
        search();
    }
    @OnItemClick(value = R.id.listview)
    protected void itemClickListener(AdapterView<?> parent,View view,int pos, long id){
        FinalSearchAllBean bean = (FinalSearchAllBean) parent.getAdapter().getItem(pos);
        if (bean == null) return;
        int type = bean.getType();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_ID, bean.getId());
        bundle.putString(Constant.KEY_STR, bean.getName());
        Class<? extends Activity> cls = null;
        switch (type) {
            case 1:
                cls = CompetitionDetailActivity.class;
                break;
            case 2:
                cls = TeamDetailActivity.class;
                break;
            case 3:
                cls = PlayerDetailActivity.class;
                break;
            case 4:
                cls = CountryDetailActivity.class;
                break;
            case 5:
                cls = OtherDetailActivity.class;
                break;
        }
        if (cls != null)
            LaunchWithExitUtils.startActivity(getActivity(), cls, bundle);
    }

}
