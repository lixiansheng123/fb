package com.yuedong.football_mad.model.bean;

import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.lib_develop.utils.FileUtils;

import java.io.Serializable;

public class User implements Serializable{

    private String id;
    private String sid;
    private String name;
    private String avatar;
    private String phone;
    private String gender;
    private String usertype;
    private int userlevel;
    private String birthday;
    private String city;
    private String workcity;
    private int worktype;
    private String realphoto;
    private String realname;
    private int qualifystate;
    private String organization;
    private String remark;
    private String hot;
    private int pos;
    private String favoritecountryid;
    private String favoriteteamid;
    private String favoritegameid;
    private String commentgood;
    private String newsgood;
    private String commentcount;
    private String newscount;
    private String postcount;
    private String Partnewsgood;
    private String totalcomment;
    private String friendcount;
    private String fanscount;
    private String collectcount;
    private String interestcount;
    private String honor;
    private String createtime;
    private String favoritecountry;
    private String favoriteteam;
    private String countrylogo;
    private String teamlogo;
    private String contact;
    private String isfriend;

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCountrylogo() {
        return countrylogo;
    }

    public void setCountrylogo(String countrylogo) {
        this.countrylogo = countrylogo;
    }

    public String getTeamlogo() {
        return teamlogo;
    }

    public void setTeamlogo(String teamlogo) {
        this.teamlogo = teamlogo;
    }

    public String getFavoritecountry() {
        return favoritecountry;
    }

    public void setFavoritecountry(String favoritecountry) {
        this.favoritecountry = favoritecountry;
    }

    public String getFavoriteteam() {
        return favoriteteam;
    }

    public void setFavoriteteam(String favoriteteam) {
        this.favoriteteam = favoriteteam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWorkcity() {
        return workcity;
    }

    public void setWorkcity(String workcity) {
        this.workcity = workcity;
    }

    public int getWorktype() {
        return worktype;
    }

    public void setWorktype(int worktype) {
        this.worktype = worktype;
    }

    public String getRealphoto() {
        return realphoto;
    }

    public void setRealphoto(String realphoto) {
        this.realphoto = realphoto;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getQualifystate() {
        return qualifystate;
    }

    public void setQualifystate(int qualifystate) {
        this.qualifystate = qualifystate;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getFavoritecountryid() {
        return favoritecountryid;
    }

    public void setFavoritecountryid(String favoritecountryid) {
        this.favoritecountryid = favoritecountryid;
    }

    public String getFavoriteteamid() {
        return favoriteteamid;
    }

    public void setFavoriteteamid(String favoriteteamid) {
        this.favoriteteamid = favoriteteamid;
    }

    public String getFavoritegameid() {
        return favoritegameid;
    }

    public void setFavoritegameid(String favoritegameid) {
        this.favoritegameid = favoritegameid;
    }

    public String getCommentgood() {
        return commentgood;
    }

    public void setCommentgood(String commentgood) {
        this.commentgood = commentgood;
    }

    public String getNewsgood() {
        return newsgood;
    }

    public void setNewsgood(String newsgood) {
        this.newsgood = newsgood;
    }

    public String getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(String commentcount) {
        this.commentcount = commentcount;
    }

    public String getNewscount() {
        return newscount;
    }

    public void setNewscount(String newscount) {
        this.newscount = newscount;
    }

    public String getPostcount() {
        return postcount;
    }

    public void setPostcount(String postcount) {
        this.postcount = postcount;
    }

    public String getPartnewsgood() {
        return Partnewsgood;
    }

    public void setPartnewsgood(String partnewsgood) {
        Partnewsgood = partnewsgood;
    }

    public String getTotalcomment() {
        return totalcomment;
    }

    public void setTotalcomment(String totalcomment) {
        this.totalcomment = totalcomment;
    }

    public String getFriendcount() {
        return friendcount;
    }

    public void setFriendcount(String friendcount) {
        this.friendcount = friendcount;
    }

    public String getFanscount() {
        return fanscount;
    }

    public void setFanscount(String fanscount) {
        this.fanscount = fanscount;
    }

    public String getCollectcount() {
        return collectcount;
    }

    public void setCollectcount(String collectcount) {
        this.collectcount = collectcount;
    }

    public String getInterestcount() {
        return interestcount;
    }

    public void setInterestcount(String interestcount) {
        this.interestcount = interestcount;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    /**
     * 从本地获取userInfo
     */
    public static boolean getUserByLocal() {
        Object o = FileUtils.readObjectFromFile("userinfo.c");
        if (o != null) {
            MyApplication.getInstance().user = ((User) o);
        }
        return false;
    }

    /**
     * 设置user到本地持久化
     */
    public static boolean setUserToLocal(User user) {
        return FileUtils.writeObjectToFile(user, "userinfo.c");
    }
}
