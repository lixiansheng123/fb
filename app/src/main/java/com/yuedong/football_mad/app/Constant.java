package com.yuedong.football_mad.app;

/**
 * Created by Administrator on 2016/6/3.
 */
public class Constant {
    public static String CALLER = "cf@android/suc";
    public static String S_KEY = "59f07214bae5f3a624b5f6c6ab62fb6a";
    public static String URL = "http://www.zqfeng.com/crazyfootball/";
    public static String URL_PIC = "http://www.zqfeng.com/";
    // 用户
    public static String URL_LONGIN = URL + "user/userlogin";
    public static String URL_MODIFY_PASSWORD = URL + "user/changePassword";
    public static String URL_ADD_DETAIL = URL + "user/addDetail";
    public static String URL_MODIFY_ADD_DETAIL = URL + "user/addDetail";
    public static String URL_REGISTER = URL + "user/userRegister";
    public static String URL_GET_USER_BY_SID = URL + "user/getUserBySid";
    // banner
    public static String URL_BANNEL_WORLD = URL + "banner/getWorldBanner";
    public static String URL_BANNEL_CHINA = URL + "banner/getChinaBanner";

    // 专题
    public static String URL_HOT_SPECIAL = URL + "special/getHotSpecial";
    public static String URL_SPECIAL_LIST = URL + "special/getSpecialList";

    // news
    public static String URL_WORLD_NEWS = URL + "news/getWorldNews";// 世界新闻
    public static String URL_HOME_NEWS = URL + "news/getHomeNews";// 国内新闻
    public static String URL_GET_CONTEST_NEWS = URL + "news/getContestNews";// 赛事新闻列表

    // 见地
    public static String URL_HOT_INSIGNLIST = URL + "news/getHotInsign";
    public static String URL_INSIGNLIST = URL + "news/getInsignList";

    //数据酷
    public static String URL_NEWS_GETDATASNEWS = URL + "news/getDataNews";
    public static String URL_GET_HOTGAME = URL + "game/getHotGame";
    public static String URL_GET_GAMELIST = URL + "game/getGameList";
    public static String URL_GET_HOTTEAM = URL + "team/getHotTeam";
    public static String URL_GET_TEAM_LIST = URL + "team/getTeamList";
    public static String URL_GET_TEAM_BY_ID = URL + "team/getTeamById";
    public static String URL_GET_HOTATHLETE = URL + "athlete/getHotAthlete";
    public static String URL_GET_ATHLETELIST = URL + "athlete/getAthleteList";
    public static String URL_GET_ATHLETE_BY_ID = URL + "athlete/getAthleteById";

    public static String URL_SEARCH_ALL = URL + "athlete/SearchAll";
    public static String URL_GET_HOTCOUNTRY = URL + "country/getHotCountry";
    public static String URL_GET_COUNTRYLIST = URL + "country/getCountryList";
    public static String URL_GET_COUNTRY_BY_ID = URL + "country/getCountryById";
    public static String URL_GET_HOTOTHER = URL + "other/getHotOther";
    public static String URL_GET_OTHERLIST = URL + "other/getOtherList";
    public static String URL_GET_OTHE_BY_ID = URL + "other/getOtherById";
    public static String URL_GETGAMEINFO_BYID = URL + "game/getGameById";
    public static String URL_GAMELIST = URL +"game/getGameList";
    public static String URL_HOTGAMELIST = URL +"game/getHotGame";



    // 评论
    public static String URL_GET_COMMENT_BY_NEWS = URL + "comment/getCommentByNews";
    public static String URL_ADD_COMMENT = URL + "comment/addComment";// 增加评论
    public static String URL_COMMENT_ZAN = URL +"comment/goodComment";
    public static String URL_COMMENT_UNZAN = URL +"comment/ungoodComment";

    // 注入
    public static boolean AUTO_INJECT = true;
    // 状态是正常的
    public static int OK = 2000000;

    public static String KEY_BOOL = "key-bool";
    public static String KEY_OBJ = "key-obj";
    public static String KEY_STR = "key-str";
    public static String KEY_ACTION = "key-action";
    public static String KEY_ID = "key-id";
}
