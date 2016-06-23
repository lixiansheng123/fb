package com.yuedong.football_mad.model.bean;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/23
 */
public class CompetitionDetailBean {
    private String id;
    private String name;
    private String abbrname;
    private String logo;
    private List<List<String>> scoreboard;
    private String establishtime;
    private String countryid;
    private String topteamid;
    private String topathleteid;
    private String worldcup;
    private String champion;
    private String worldrank;
    private String shooter;
    private long createtime;
    private long updatetime;
    private String mark;
    private String hot;
    private String country;
    private String topathlete;
    private String topteam;

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

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

    public String getAbbrname() {
        return abbrname;
    }

    public void setAbbrname(String abbrname) {
        this.abbrname = abbrname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<List<String>> getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(List<List<String>> scoreboard) {
        this.scoreboard = scoreboard;
    }

    public String getEstablishtime() {
        return establishtime;
    }

    public void setEstablishtime(String establishtime) {
        this.establishtime = establishtime;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getTopteamid() {
        return topteamid;
    }

    public void setTopteamid(String topteamid) {
        this.topteamid = topteamid;
    }

    public String getTopathleteid() {
        return topathleteid;
    }

    public void setTopathleteid(String topathleteid) {
        this.topathleteid = topathleteid;
    }

    public String getWorldcup() {
        return worldcup;
    }

    public void setWorldcup(String worldcup) {
        this.worldcup = worldcup;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getWorldrank() {
        return worldrank;
    }

    public void setWorldrank(String worldrank) {
        this.worldrank = worldrank;
    }

    public String getShooter() {
        return shooter;
    }

    public void setShooter(String shooter) {
        this.shooter = shooter;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTopathlete() {
        return topathlete;
    }

    public void setTopathlete(String topathlete) {
        this.topathlete = topathlete;
    }

    public String getTopteam() {
        return topteam;
    }

    public void setTopteam(String topteam) {
        this.topteam = topteam;
    }
}
