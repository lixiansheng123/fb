package com.yuedong.football_mad.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.DataChooseAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.FinalSearchAllBean;
import com.yuedong.football_mad.model.bean.SearchAllBean;
import com.yuedong.football_mad.model.bean.SearchAllRespBean;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人信息选择数据库列表
 */
public class ChooseDataKuListActivity extends BaseActivity {
    private int cur_action ;
    public static final int ACTION_COUNTRY = 0x00111;
    public static final int ACTION_TEAM = 0x00222;
    @ViewInject(R.id.et_search)
    private EditText etSearch;
    @ViewInject(R.id.listview)
    private ListView listView;
    private String listTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cur_action = getIntent().getIntExtra(Constant.KEY_ACTION, ACTION_COUNTRY);
        buildUi(new TitleViewHelper(this).getTitle1NoRightCenterTitle(R.drawable.ic_round_return,"数据库",null),
                R.layout.activity_choose_data_ku_list);
    }

    @Override
    protected void ui() {

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(listTask)){
            SearchAllRespBean bean = (SearchAllRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    private void renderUi(SearchAllRespBean.List list) {
        List<FinalSearchAllBean> data = new ArrayList<>();
        List<SearchAllBean> country = list.getCountry();
        List<SearchAllBean> team = list.getTeam();
        if(cur_action == ACTION_COUNTRY){
            if(country!=null){
                for (SearchAllBean s : country){
                    FinalSearchAllBean bean = new FinalSearchAllBean();
                    tobean(bean,s,4);
                    data.add(bean);
                }
            }
        }else{
            if(team!=null){
                for (SearchAllBean s : team){
                    FinalSearchAllBean bean = new FinalSearchAllBean();
                    tobean(bean,s,4);
                    data.add(bean);
                }
            }
        }
        final DataChooseAdapter adapter = new DataChooseAdapter(activity,data);
        listView.setAdapter(adapter);
        adapter.setChooseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                FinalSearchAllBean item = adapter.getItem(position);
                Intent data = new Intent();
                data.putExtra(Constant.KEY_ID,item.getId());
                data.putExtra(Constant.KEY_STR,item.getLogo());
                setResult(cur_action, data);
                back();
            }
        });
    }

    private void tobean(FinalSearchAllBean bean, SearchAllBean s, int i) {
        bean.setType(i);
        bean.setId(s.getId());
        bean.setName(s.getName());
        bean.setLogo(s.getLogo());
    }

    @OnClick({R.id.btn_search})
    protected void clickEvent(View view){
        switch(view.getId()){
            case R.id.btn_search:
                String text = etSearch.getText().toString();
                if(TextUtils.isEmpty(text)){
                    T.showShort(activity,"请输入关键字");
                    return;
                }
                String url = Constant.URL_SEARCH_COUNTRY_TEAM;
                Map<String,String> post = new HashMap<>();
                post.put("searchkey",text);
                listTask = RequestHelper.post(url,post,SearchAllRespBean.class,false,false,ChooseDataKuListActivity.this);
                break;
        }
    }
}
