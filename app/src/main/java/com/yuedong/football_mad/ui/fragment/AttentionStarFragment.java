package com.yuedong.football_mad.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyAttentionStarAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.AttentionStarBean;
import com.yuedong.football_mad.model.bean.AttentionStarRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/7/5
 */
public class AttentionStarFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<AttentionStarBean> refreshProxy = new RefreshProxy<>();
    private String hotListTask,attentionTask,cancelAttentionTask;
    private User loginUser;
    private boolean one = true;
    private MyAttentionStarAdapter adapter;
    private ImageView ivIcon;// 删除按钮和关注按钮
    private AttentionStarBean starBean;

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_common_list;
    }

    @Override
    public void ui() {
        loginUser = MyApplication.getInstance().getLoginUser();
        getHotList();

    }

    public void editList(boolean edit){
        if(!createUi)return;
        adapter.isEdit = edit;
        adapter.notifyDataSetChanged();
    }

    private void getHotList() {
        User loginUser = MyApplication.getInstance().getLoginUser();
        hotListTask = RequestHelper.post(Constant.URL_NEWS_HOT_STAR, DataUtils.getSidPostMap(loginUser.getSid()), AttentionStarRespBean.class, false, false, this);
    }


    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(hotListTask)) {
            final AttentionStarRespBean bean = (AttentionStarRespBean) data;
            final List<AttentionStarBean> hotList = bean.getDataList();
            refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<AttentionStarBean>() {
                @Override
                public BaseAdapter<AttentionStarBean> getAdapter(List<AttentionStarBean> data) {
                    adapter = new MyAttentionStarAdapter(getActivity(), data);
                    adapter.setOnDeleteClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            starBean   = (AttentionStarBean) v.getTag();
                            switch(v.getId()){
                                case R.id.iv_delete:
                                    cancelAttentionTask=   DataUtils.attentionBallStar(getActivity(),loginUser.getSid(),starBean.getId(),AttentionStarFragment.this,false);
                                    break;
                                case R.id.iv_attention:
                                    ivIcon = (ImageView) v;
                                    boolean isAttention = false;
                                    if("0".equals(starBean.getInterest())) isAttention = true;
                                    User loginUser = MyApplication.getInstance().getLoginUser();
                                    final boolean finalIsAttention = isAttention;
                                    attentionTask=   DataUtils.attentionBallStar(getActivity(), loginUser.getSid(), starBean.getId(),AttentionStarFragment.this,isAttention);
                                    break;
                            }
                        }
                    });
                    return adapter;
                }

                @Override
                public void executeTask(int page, int count, int max, final VolleyNetWorkCallback listener, int type) {
                    if (type == 1) one = true;
                    Map<String, String> post = DataUtils.getListPostMapHasSId(page + "", count + "", "", loginUser.getSid());
                    RequestHelper.post(Constant.URL_USER_ATTENTION_STAR, post, AttentionStarRespBean.class, false, false, new VolleyNetWorkCallback() {                        @Override
                        public void onNetworkStart(String tag) {
                            listener.onNetworkStart(tag);
                        }

                        @Override
                        public void onNetworkSucceed(String tag, BaseResponse data) {
                            if (one) {
                                one = false;
                                AttentionStarRespBean attentionStarRespBean = (AttentionStarRespBean) data;
                                List<AttentionStarBean> finalList = new ArrayList<AttentionStarBean>();
                                finalList.addAll(hotList);
                                finalList.addAll(attentionStarRespBean.getDataList());
                                attentionStarRespBean.getData().setList(finalList);
                            }
                            listener.onNetworkSucceed(tag, data);

                        }

                        @Override
                        public void onNetworkError(String tag, VolleyError error) {
                            listener.onNetworkError(tag, error);
                        }
                    });
                }

                @Override
                public void networkSucceed(ListResponse<AttentionStarBean> list) {

                }

                @Override
                public void contentIsEmpty() {

                }
            });
        }else if(tag.equals(attentionTask)){
            if(starBean.getInterest().equals("0")) {
                starBean.setInterest("1");
                ivIcon.setImageResource(R.drawable.ic_attention_select);
                AttentionStarBean newBean = new AttentionStarBean();
                newBean.setName(starBean.getName());
                newBean.setAvatar(starBean.getAvatar());
                newBean.setId(starBean.getId());
                adapter.getData().add(newBean);
                adapter.notifyDataSetChanged();
            } else {
                starBean.setInterest("0");
                ivIcon.setImageResource(R.drawable.ic_attention_unselect);
                adapter.deleteItem(starBean.getId());
            }
        }else if(tag.equals(cancelAttentionTask)){
            adapter.getData().remove(starBean);
            adapter.notifyDataSetChanged();
        }
    }
}
