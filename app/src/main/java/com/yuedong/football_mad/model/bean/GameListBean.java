package com.yuedong.football_mad.model.bean;

/**
 * @author 俊鹏 on 2016/7/1
 */
public class GameListBean {
    private String id;
    private String live;
    private String contesttime;
    private String time;
    private String score;
    private String hometeamid;
    private String guestteamid;
    private String hometeam;
    private String hometeamlogo;
    private String guestteam;
    private String guestteamlogo;
    private String make;
    public boolean attention;// 是否关注了


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getContesttime() {
        return contesttime;
    }

    public void setContesttime(String contesttime) {
        this.contesttime = contesttime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHometeamid() {
        return hometeamid;
    }

    public void setHometeamid(String hometeamid) {
        this.hometeamid = hometeamid;
    }

    public String getGuestteamid() {
        return guestteamid;
    }

    public void setGuestteamid(String guestteamid) {
        this.guestteamid = guestteamid;
    }

    public String getHometeam() {
        return hometeam;
    }

    public void setHometeam(String hometeam) {
        this.hometeam = hometeam;
    }

    public String getHometeamlogo() {
        return hometeamlogo;
    }

    public void setHometeamlogo(String hometeamlogo) {
        this.hometeamlogo = hometeamlogo;
    }

    public String getGuestteam() {
        return guestteam;
    }

    public void setGuestteam(String guestteam) {
        this.guestteam = guestteam;
    }

    public String getGuestteamlogo() {
        return guestteamlogo;
    }

    public void setGuestteamlogo(String guestteamlogo) {
        this.guestteamlogo = guestteamlogo;
    }
}
