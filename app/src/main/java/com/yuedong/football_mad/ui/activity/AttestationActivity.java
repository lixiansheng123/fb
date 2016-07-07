package com.yuedong.football_mad.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Config;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.LocalPhotoActivity;
import com.yuedong.football_mad.model.bean.GetUserInfoByIdResBean;
import com.yuedong.football_mad.model.bean.HeadRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.view.SelectPhotoDialog;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.ioc.annotation.event.OnCompoundButtonCheckedChange;
import com.yuedong.lib_develop.utils.Base64;
import com.yuedong.lib_develop.utils.DimenUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.FileUtils;
import com.yuedong.lib_develop.utils.ImageZoomUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.Map;

/**
 * 从业认证
 */
public class AttestationActivity extends LocalPhotoActivity implements SelectPhotoDialog.OnSelectPicPopCallback {
    @ViewInject(R.id.iv_user_head)
    private RoundImageView ivHead;
    @ViewInject(R.id.iv_other)
    private RoundImageView ivOther;
    @ViewInject(R.id.cb1)
    private ImageView cb1;
    @ViewInject(R.id.cb2)
    private ImageView cb2;
    @ViewInject(R.id.cb3)
    private ImageView cb3;
    @ViewInject(R.id.cb4)
    private ImageView cb4;
    @ViewInject(R.id.et_say)
    private EditText etSay;
    @ViewInject(R.id.et_realname)
    private EditText etRealName;
    @ViewInject(R.id.et_city)
    private EditText etCity;
    @ViewInject(R.id.et_jigou)
    private EditText etJigou;
    @ViewInject(R.id.et_contact)
    private EditText etContact;
    @ViewInject(R.id.view_gap)
    private View gap;
    private ImageView[] cbs = new ImageView[4];
    private SelectPhotoDialog selectPhotoDialog;
    private int cur_photo = PHOTO_HEAD;
    private static final int PHOTO_HEAD = 0;
    private static final int PHOTO_OHTER = 1;
    private User loginUser;
    private int dp81;
    private String realPic;// 真实头像
    private String otherPic;// 相关照片
    private int worktype = 1;// 工作类型
    private String uploadHeadTask,uploadRealTask,userInfoTask,submitTask;
    private Map<String,String> picPost;
    private String sayStr;
    private String realNameStr;
    private String cityStr;
    private String jigouStr;
    private String contactStr;
    private String realPicNet;// 线上上传的真是头像地址
    private String otherPicNet;// 线上上传的认证图片地址
    private TitleViewHelper titleViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleViewHelper = new TitleViewHelper(this);
        buildUi(titleViewHelper.getTitle1NomarlCenterIcon(R.drawable.ic_round_return, R.drawable.ic_renzheng_logo, R.drawable.ic_round_renzheng, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        }), R.layout.activity_attestation);
        dp81 = DimenUtils.dip2px(activity,81);
        loginUser = MyApplication.getInstance().getLoginUser();
        selectPhotoDialog = new SelectPhotoDialog(activity);
        autoLoadView = false;
    }

    /**
     * 上传认证信息
     */
    private void upload() {
        if(!check())return;
        showLoadView(true);
        picPost = DataUtils.getSidPostMap(loginUser.getSid());
        picPost.put("encode", "jpg");
        if(realPic!=null){
            uploadHead();
        }else{
            uploadReal();
        }
    }
    /* 上传头像 */
    private void uploadHead(){
        String s = ImageZoomUtils.compressImageToFile(realPic, Config.DIR_PHOTO_UPLOAD, 150);
        L.d("头像上传图片路径" + s);
        String realPicBase64 = Base64.encode(FileUtils.getFileContent(s));
        picPost.put("avatar", realPicBase64);
        uploadHeadTask = RequestHelper.post(Constant.URL_USER_UPLOAD_HEAD, picPost, HeadRespBean.class, false, false, AttestationActivity.this);
    }

    /* 上传认证 */
    private void uploadReal(){
        String s2 = ImageZoomUtils.compressImageToFile(otherPic, Config.DIR_PHOTO_UPLOAD, 250);
        L.d("证件上传图片路径" + s2);
        String otherPicBase64 = Base64.encode(FileUtils.getFileContent(s2));
        picPost.remove("avatar");
        picPost.put("realphoto", otherPicBase64);
        uploadRealTask= RequestHelper.post(Constant.URL_USER_UPLOAD_REAL, picPost,HeadRespBean.class,false,false,AttestationActivity.this);
    }

    private boolean check() {
        if(otherPic == null) {
            T.showShort(activity,"必须上传认证相片");
            return false;
        }
        sayStr = etSay.getText().toString();
        realNameStr = etRealName.getText().toString();
        cityStr = etCity.getText().toString();
        jigouStr = etJigou.getText().toString();
        contactStr =etContact.getText().toString();
        if(TextUtils.isEmpty(realNameStr)){
            T.showShort(activity,"必须填写认证姓名");
            return false;
        }
        if(TextUtils.isEmpty(cityStr)){
            T.showShort(activity,"必须填写从业城市");
            return false;
        }
        if(TextUtils.isEmpty(jigouStr)){
            T.showShort(activity,"必须填写所属机构");
            return false;
        }
        if(TextUtils.isEmpty(contactStr)){
            T.showShort(activity,"必须填写联系方式");
            return false;
        }

        return  true;
    }

    @Override
    protected void ui() {
        cbs[0] =cb1;
        cbs[1] =cb2;
        cbs[2] =cb3;
        cbs[3] =cb4;
        selectPhotoDialog.setOnSelectPicPopCallback(this);
        // 认证判断 只有认证中才渲染数据
        int qualifystate = getIntent().getExtras().getInt(Constant.KEY_INT);
        if(qualifystate == 1){
            renderUi();
        }
    }

    private void renderUi() {
        titleViewHelper.getTitle1Right().setClickable(false);
        ViewUtils.showLayout(gap);
        User loginUser = MyApplication.getInstance().getLoginUser();
        String avatar = loginUser.getAvatar();
        if(!TextUtils.isEmpty(avatar))
            DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(UrlHelper.checkUrl(avatar)),ivHead);
        String realphoto = loginUser.getRealphoto();
        if(!TextUtils.isEmpty(realphoto))
            DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(realphoto),ivOther);
        int worktype = loginUser.getWorktype();
        for(ImageView c :cbs)c.setSelected(false);
        switch(worktype){
            case 1:
            default:
                cb1.setSelected(true);
                break;
            case 2:
                cb2.setSelected(true);
                break;
            case 3:
                cb3.setSelected(true);
                break;
            case 4:
                cb4.setSelected(true);
                break;
        }
        String say = loginUser.getRemark();
        if(!TextUtils.isEmpty(say)){
            etSay.setText(say);
        }
        etRealName.setText(loginUser.getRealname());
        etCity.setText(loginUser.getWorkcity());
        etJigou.setText(loginUser.getOrganization());
        etContact.setText(loginUser.getPhone());
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(uploadHeadTask)){
            HeadRespBean bean = (HeadRespBean) data;
            realPicNet = UrlHelper.checkUrl(bean.getData().getAvatar());
            L.d("上传头像成功地址为:"+ realPicNet);
            uploadReal();
        }else if(tag.equals(uploadRealTask)){
            HeadRespBean bean = (HeadRespBean) data;
            otherPicNet =UrlHelper.checkUrl(bean.getData().getRealphoto());
            L.d("上传证件成功地址为:"+ otherPicNet);
            // 提交所有信息
            Map<String, String> post = DataUtils.getSidPostMap(loginUser.getSid());
            post.put("remark",sayStr);
            post.put("worktype",worktype+"");
            post.put("realname",realNameStr);
            post.put("remark",sayStr);
            post.put("workcity",cityStr);
            post.put("organization",jigouStr);
            // TODO 缺少联系方式字段
//            post.put("phone", contactStr);
           submitTask =  RequestHelper.post(Constant.URL_ADD_DETAIL,post,BaseResponse.class,false,false,AttestationActivity.this);
        }else if(tag.equals(submitTask)){
            // 更新个人信息
            userInfoTask=  CommonHelper.getUserInfo(loginUser.getSid(),AttestationActivity.this);
        }else if(tag.equals(userInfoTask)){
            showLoadView(false);
            GetUserInfoByIdResBean bean = (GetUserInfoByIdResBean) data;
            MyApplication.getInstance().setLoginuser(bean.getData().getList());
            MyApplication.getInstance().userInfoChange = true;
            T.showShort(activity,"提交认证成功");
            back();
        }
    }
    @OnClick({R.id.cb1,R.id.cb2,R.id.cb3,R.id.cb4})
    protected void selClickEvent(View view){
        for(ImageView iv :cbs)iv.setSelected(false);
        ((ImageView)view).setSelected(true);
        switch (view.getId()) {
            case R.id.cb1:
                worktype  = 1;
                break;
            case R.id.cb2:
                worktype  = 2;
                break;
            case R.id.cb3:
                worktype  = 3;
                break;
            case R.id.cb4:
                worktype  = 4;
                break;
        }
    }

    @OnClick({R.id.rl_head,R.id.rl_other,R.id.view_gap,R.id.cb1,R.id.cb2,R.id.cb3,R.id.cb4})
    protected void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.rl_head:
                cur_photo = PHOTO_HEAD;
                selectPhotoDialog.show();
                break;

            case R.id.rl_other:
                cur_photo = PHOTO_OHTER;
                selectPhotoDialog.show();
                break;

            case R.id.view_gap:
                T.showShort(activity,"认证中不可操作");
                break;

        }
    }

    @Override
    public void onTakePic() {
        getPhotoByTake(true);
    }

    @Override
    public void onGetPic() {
        getPhotoByPhotoSet(true);
    }

    @Override
    protected void getPicSucceed(String path) {
        Bitmap bitmap = ImageZoomUtils.decodeSampleBitmapFromPath(path, dp81, dp81);
        if(cur_photo == PHOTO_HEAD) {
            realPic = path;
            ivHead.setImageBitmap(bitmap);
        }else if(cur_photo == PHOTO_OHTER){
            otherPic = path;
            ivOther.setImageBitmap(bitmap);
        }
    }
}
