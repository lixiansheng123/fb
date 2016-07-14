package com.yuedong.football_mad.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.OnNotifyListener;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.FansBean;
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
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.view.RoundImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/7/12
 */
public class MyFansFragment extends BaseFragment{
    private String fansListTask,addFriendTask;
    @ViewInject(R.id.expandableListview)
    private ExpandableListView exListView;
    private User loginUser;
    private List<String> group = new ArrayList<>();
    private List<List<FansBean>> child = new ArrayList<>();
    private ImageView ivAddFriend;
    private String isFriend;
    private OnNotifyListener notifyListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notifyListener = (OnNotifyListener) getActivity();
    }

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_friend;
    }

    @Override
    public void ui() {
        if(!createUi)return;
        loginUser = MyApplication.getInstance().getLoginUser();
        fansListTask=  RequestHelper.post(Constant.URL_FANS_LIST_BY_USER, DataUtils.getListPostMapHasSId("1", "500", "0", loginUser.getSid()),BaseResponse.class,true,true,this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(fansListTask)){
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
                            List<FansBean> fansBeans = new ArrayList<>();
                            JSONArray jsonArray = listJsonObj.getJSONArray(key);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject job = (JSONObject) jsonArray.get(i);
                                String id = job.getString("id");
                                String name = job.getString("name");
                                String avatar = job.getString("avatar");
                                int userlevel = job.getInt("userlevel");
                                String shortLetter = job.getString("short");
                                String isfriend = job.getString("isfriend");
                                FansBean fansBean = new FansBean();
                                fansBean.id = id;
                                fansBean.name = name;
                                fansBean.avatar = avatar;
                                fansBean.userlevel = userlevel;
                                fansBean.shortLetter = shortLetter;
                                fansBean.isfriend = isfriend;
                                fansBeans.add(fansBean);
                            }
                            child.add(fansBeans);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            MyAdapter myAdapter = new MyAdapter();
            exListView.setAdapter(myAdapter);
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
            for (int i = 0; i < group.size(); i++) {
                exListView.expandGroup(i);
            }
            exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    FansBean fansBean = child.get(groupPosition).get(childPosition);
                    Bundle data = new Bundle();
                    data.putString(Constant.KEY_STR2, fansBean.id);
                    LaunchWithExitUtils.startActivity(getActivity(), UserInfoActivity.class, data);
                    return true;
                }
            });
        }else if(tag .equals(addFriendTask)){
            T.showShort(getActivity(), "增加好友成功");
            ivAddFriend.setImageResource(R.drawable.ic_friend_already_add);
            isFriend = "1";
            notifyListener.onNotifyAddFriend();
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
            final FansBean fansBean = child.get(groupPosition).get(childPosition);
            DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(fansBean.userlevel);
            RoundImageView ivHead= viewHolder.getIdByView(R.id.iv_user_head);
            TextView tvLevel = viewHolder.getIdByView(R.id.tv_level);
            ImageView ivIcon = viewHolder.getIdByView(R.id.iv_icon);
            viewHolder.getIdByView(R.id.rl_head).setBackgroundResource(userLevelDisplayInfo.headBg);
            DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(fansBean.avatar), ivHead);
            viewHolder.setText(R.id.tv_name, fansBean.name);
            tvLevel.setText(com.yuedong.lib_develop.utils.TextUtils.addTextColor(userLevelDisplayInfo.textDesc, 0, 2, userLevelDisplayInfo.partTextColor));
            final String isfriend = fansBean.isfriend;
            ViewGroup.LayoutParams layoutParams = ivIcon.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            if(isfriend.equals("0")){
                ivIcon.setImageResource(R.drawable.ic_friend_add);
                ivIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivAddFriend = (ImageView) v;
                        isFriend = isfriend;
                        Map<String, String> post = DataUtils.getSidPostMap(loginUser.getSid());
                        post.put("friendid",fansBean.id);
                       addFriendTask =  RequestHelper.post(Constant.URL_ADD_FRIEND,post,BaseResponse.class,false,false,MyFansFragment.this);
                    }
                });
            }else{
                ivIcon.setImageResource(R.drawable.ic_friend_already_add);
                ivIcon.setOnClickListener(null);
            }
            return viewHolder.getConvertView();
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
