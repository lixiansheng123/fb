package com.yuedong.football_mad.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.HotFriendAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.DbLookFriendBean;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.IdAvatarNameUserLeveLastPublishTimelBean;
import com.yuedong.football_mad.model.bean.IdAvatarNameUserLevelRespBean;
import com.yuedong.football_mad.model.bean.MyFriendBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.UserInfoActivity;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;
import com.yuedong.lib_develop.view.SupportScrollConflictListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/7/12
 */
public class MyFriendFragment extends BaseFragment {
    private String hotFriendTask,friendListTask,deleteFriendTask;
    @ViewInject(R.id.expandableListview)
    private ExpandableListView exListView;
    @ViewInject(R.id.spListView)
    private SupportScrollConflictListView spListView;
    private User loginUser;
    private List<String> group = new ArrayList<>();
    private List<List<MyFriendBean>> child = new ArrayList<>();
    private MyAdapter expandableAdapter;
    private List<DbLookFriendBean> lookFriendLists;
    // 是否编辑列表
    private boolean isEditList = false;

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_friend;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void ui() {
        autoLoadView = false;
        loginUser = MyApplication.getInstance().getLoginUser();
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.splistview, null);
        exListView.addHeaderView(headerView,null,false);
        spListView = (SupportScrollConflictListView) headerView.findViewById(R.id.spListView);
        // 获取观看好友资料的信息
        lookFriendLists = DataUtils.getLookFriendList(loginUser.getId());
        getHotFriend();
    }

    /**
     * 获取推荐好友
     */
    private void getHotFriend() {
        showLoadView(true);
        hotFriendTask = RequestHelper.post(Constant.URL_HOT_FRIEND_LIST, null, IdAvatarNameUserLevelRespBean.class, true, true, this);
    }
    /**
     * 获取好友列表
     */
    public void getFriendList(){
        friendListTask = RequestHelper.post(Constant.URL_FRIEND_LIST_BY_USER, DataUtils.getListPostMapHasSId("1","500","0",loginUser.getSid()),BaseResponse.class,true,true,this);
    }

    /**
     * 编辑列表
     * @param isEdit
     */
    public void editList(boolean isEdit){
        this.isEditList = isEdit;
        expandableAdapter.notifyDataSetChanged();
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(hotFriendTask)) {
            IdAvatarNameUserLevelRespBean bean = (IdAvatarNameUserLevelRespBean) data;
            HotFriendAdapter adapter = new HotFriendAdapter(getActivity(),bean.getData().getList());
            adapter.setLookFriendBeans(lookFriendLists);
            spListView.setAdapter(adapter);
            spListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    IdAvatarNameUserLeveLastPublishTimelBean bean = (IdAvatarNameUserLeveLastPublishTimelBean) parent.getAdapter().getItem(position);
                    Bundle data = new Bundle();
                    data.putString(Constant.KEY_STR2, bean.getId());
                    LaunchWithExitUtils.startActivity(getActivity(), UserInfoActivity.class, data);


                }
            });
            expandableAdapter =  new MyAdapter();
            exListView.setAdapter(expandableAdapter);
            exListView.setGroupIndicator(null);
            // 不能收缩
            exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    // TODO Auto-generated method stub
                    return true;
                }
            });
            getFriendList();
        }else if(tag.equals(friendListTask)){
            showLoadView(false);
            String json = data.getJson();
            try {
                JSONObject jsObj = new JSONObject(json);
                JSONObject dataJsonObj = jsObj.getJSONObject("data");
                if(dataJsonObj != null && !TextUtils.isEmpty(dataJsonObj.toString())){
                    JSONObject listJsonObj = dataJsonObj.getJSONObject("list");
                    if(listJsonObj != null && !TextUtils.isEmpty(listJsonObj.toString())){
                        Iterator<String> keys = listJsonObj.keys();
                        while (keys.hasNext()){
                            String key = keys.next();
                            group.add(key);
                            List<MyFriendBean> myFriendBeanList = new ArrayList<>();
                            JSONArray jsonArray = listJsonObj.getJSONArray(key);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject job = (JSONObject) jsonArray.get(i);
                                String id = job.getString("id");
                                String name = job.getString("name");
                                String avatar = job.getString("avatar");
                                int userlevel = job.getInt("userlevel");
                                String shortLetter = job.getString("short");
                                long lastpublishtime = job.getLong("lastpublishtime");
                                MyFriendBean myFriendBean = new MyFriendBean();
                                myFriendBean.id = id;
                                myFriendBean.name = name;
                                myFriendBean.avatar = avatar;
                                myFriendBean.userlevel = userlevel;
                                myFriendBean.shortLetter = shortLetter;
                                myFriendBean.lastpublishtime = lastpublishtime;
                                myFriendBeanList.add(myFriendBean);
                            }
                            child.add(myFriendBeanList);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            L.d("group-size;" + group.size() + "====child-size" + child.size());
            expandableAdapter.notifyDataSetChanged();
            for (int i = 0; i < group.size(); i++) {
                exListView.expandGroup(i);
            }
            exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    MyFriendBean myFriendBean = child.get(groupPosition).get(childPosition);
                    Bundle data = new Bundle();
                    data.putString(Constant.KEY_STR2, myFriendBean.id);
                    LaunchWithExitUtils.startActivity(getActivity(), UserInfoActivity.class, data);
                    return true;
                }
            });
        }else if(tag.equals(deleteFriendTask)){
            getFriendList();
        }
    }


    public class MyAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return group == null ? 0 :group.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return child.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return group.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child.get(childPosition).get(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = ViewHolder.get(getActivity(), convertView, parent, R.layout.layout_myballfriend_tag, groupPosition);
            viewHolder.setText(R.id.tv_label,group.get(groupPosition));
            return viewHolder.getConvertView();
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = ViewHolder.get(getActivity(),convertView,parent,R.layout.layout_myballfriend_content,childPosition);
            final MyFriendBean myFriendBean = child.get(groupPosition).get(childPosition);
            DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(myFriendBean.userlevel);
            RoundImageView ivHead= viewHolder.getIdByView(R.id.iv_user_head);
            TextView tvLevel = viewHolder.getIdByView(R.id.tv_level);
            ImageView ivIcon = viewHolder.getIdByView(R.id.iv_icon);
            ImageView ivDelete = viewHolder.getIdByView(R.id.iv_delete);
            viewHolder.getIdByView(R.id.rl_head).setBackgroundResource(userLevelDisplayInfo.headBg);
            DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(myFriendBean.avatar), ivHead);
            viewHolder.setText(R.id.tv_name, myFriendBean.name);
            tvLevel.setText(com.yuedong.lib_develop.utils.TextUtils.addTextColor(userLevelDisplayInfo.textDesc, 0, 2, userLevelDisplayInfo.partTextColor));
            // 处理红点
            boolean light = DataUtils.redPointLignt(myFriendBean.id,loginUser.getId(),myFriendBean.lastpublishtime,lookFriendLists);
            if(light) ViewUtils.showLayout(ivIcon);else ViewUtils.hideLayout(ivIcon);
            if(isEditList){
                ViewUtils.showLayout(ivDelete);
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> post = DataUtils.getSidPostMap(loginUser.getSid());
                        post.put("friendid",myFriendBean.id);
                        deleteFriendTask = RequestHelper.post(Constant.URL_DELETE_FRIEND,post,BaseResponse.class,false,false,MyFriendFragment.this);
                    }
                });
            }else{
                ViewUtils.hideLayout(ivDelete);
                ivDelete.setOnClickListener(null);
            }
            return viewHolder.getConvertView();
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
