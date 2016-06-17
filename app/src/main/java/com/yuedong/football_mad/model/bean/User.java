package com.yuedong.football_mad.model.bean;

import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.lib_develop.utils.FileUtils;

import java.io.Serializable;

public class User implements Serializable{
    private String id;
    private String name;
    private String password;
    private String avatar;
    private String usertype;
    private String birthday;
    private String favoritecountryid;
    private String favoriteteamid;
    private String favoritegameid;
    private String gender;
    private String city;
    private String phone;
    private String pos;
    private String favoritenews;
    private String favoritedata;
    private String createtime;
    private String userlevel;
    private String sid;
    private String favoriteteam;
    private String favoritecountry;
    private String favoritegame;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getFavoritenews() {
        return favoritenews;
    }

    public void setFavoritenews(String favoritenews) {
        this.favoritenews = favoritenews;
    }

    public String getFavoritedata() {
        return favoritedata;
    }

    public void setFavoritedata(String favoritedata) {
        this.favoritedata = favoritedata;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFavoriteteam() {
        return favoriteteam;
    }

    public void setFavoriteteam(String favoriteteam) {
        this.favoriteteam = favoriteteam;
    }

    public String getFavoritecountry() {
        return favoritecountry;
    }

    public void setFavoritecountry(String favoritecountry) {
        this.favoritecountry = favoritecountry;
    }

    public String getFavoritegame() {
        return favoritegame;
    }

    public void setFavoritegame(String favoritegame) {
        this.favoritegame = favoritegame;
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
