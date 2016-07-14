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
    public static String URL_USER_HONOR = URL+"user/getUserHonor";
    public static String URL_ALL_HONOR = URL+"user/getAllHonor";
    public static String URL_HOT_FRIEND_LIST = URL+"user/getHotFriend";
    public static String URL_USER_MSG = URL+"user/getUserNotify";
    public static String URL_USER_SYSTEMMSG = URL+"user/getSystemNotify";
    public static String URL_FRIEND_LIST_BY_USER = URL +"user/getFriend";
    public static String URL_FANS_LIST_BY_USER = URL +"user/getFans";
    public static String URL_ADD_FRIEND = URL +"user/addFriend";
    public static String URL_DELETE_FRIEND = URL +"user/delFriend";
    public static String URL_GET_GAMELIST = URL + "game/getGameList";
    public static String URL_GET_HOTTEAM = URL + "team/getHotTeam";
    public static String URL_GET_TEAM_LIST = URL + "team/getTeamList";
    public static String URL_GET_TEAM_BY_ID = URL + "team/getTeamById";
    public static String URL_GET_HOTATHLETE = URL + "athlete/getHotAthlete";
    public static String URL_GET_ATHLETELIST = URL + "athlete/getAthleteList";
    public static String URL_GET_ATHLETE_BY_ID = URL + "athlete/getAthleteById";
    public static String URL_GET_USERINFO = URL + "user/getUserInfo";
    public static String URL_SEARCH_ALL = URL + "athlete/SearchAll";
    public static String URL_GET_HOTCOUNTRY = URL + "country/getHotCountry";
    public static String URL_GET_COUNTRYLIST = URL + "country/getCountryList";
    public static String URL_GET_COUNTRY_BY_ID = URL + "country/getCountryById";
    public static String URL_GET_HOTOTHER = URL + "other/getHotOther";
    public static String URL_LONGIN = URL + "user/userlogin";
    public static String URL_MODIFY_PASSWORD = URL + "user/changePassword";
    public static String URL_ADD_DETAIL = URL + "user/addDetail";
    public static String URL_MODIFY_ADD_DETAIL = URL + "user/addDetail";
    public static String URL_REGISTER = URL + "user/userRegister";
    public static String URL_GET_USER_BY_SID = URL + "user/getUserBySid";
    public static String URL_USER_CODE_BY_NEW = URL + "user/getCodeByNewUser";
    public static String URL_USER_CODE_BY_OLD = URL + "user/getCodeByOldUser";
    public static String URL_USER_CHECK_CODE = URL + "user/checkCode";
    public static String URL_USER_CHANGEPASSWORD_BYPHONE = URL + "user/changePasswordByPhone";
    public static String URL_USER_UPLOAD_HEAD = URL +"user/changeAvatar";
    public static String URL_USER_UPLOAD_REAL = URL +"user/changeRealPhoto";
    // banner
    public static String URL_BANNEL_WORLD = URL + "banner/getWorldBanner";
    public static String URL_BANNEL_CHINA = URL + "banner/getChinaBanner";

    // 专题
    public static String URL_HOT_SPECIAL = URL + "special/getHotSpecial";
    public static String URL_SPECIAL_LIST = URL + "special/getSpecialList";
    public static String URL_SPECIAL_BY_ID = URL + "special/getSpecialById";

    // news
    public static String URL_USER_NEWS = URL +"news/getOwnNews";// 用户新闻列表
    public static String URL_NEWS_LIST = URL +"news/getNewsList";// 用户新闻列表
    public static String URL_WORLD_NEWS = URL + "news/getWorldNews";// 世界新闻
    public static String URL_HOME_NEWS = URL + "news/getHomeNews";// 国内新闻
    public static String URL_GET_CONTEST_NEWS = URL + "news/getContestNews";// 赛事新闻列表
    public static String URL_START_SAY_LIST = URL + "news/getStarNews";
    public static String URL_NEWS_BY_ID = URL + "news/getNewsById";
    public static String URL_INTEREST_STAR = URL + "news/InterestStar";// 关注球星说作者
    public static String URL_UNINTEREST_STAR = URL + "news/UnInterestStar";// 取消关注球星说作者
    public static String URL_NEWS_GOODS = URL + "news/goodNews";// 新闻点赞
    public static String URL_NEWS_NOGOODS = URL + "news/poorWrite";
    public static String URL_NEWS_AD_MORE = URL + "news/rubishAdv";
    public static String URL_NEWS_PLAGIARIZE = URL + "news/badCopy";
    public static String URL_NEWS_666_LIST = URL + "news/getExcellentNews";
    public static String URL_NEWS_HOT_STAR = URL + "news/getHotInterestStar";
    public static String URL_USER_ATTENTION_STAR = URL + "news/getUserInterestStar";// 用户关注的球星
    public static String URL_NEWS_INTEREST = URL + "news/InterestNews";// 关注新闻/专题
    public static String URL_NEWS_UNINTEREST = URL + "news/UnInterestNews";// 取消关注新闻/专题
    public static String URL_COLLECT_NEWS_LIST = URL + "news/getUserInterestNews";
    public static String URL_QIUTAN_HOTLIST = URL + "news/getHotNewsWithAuthor";
    public static String URL_QIUTAN_NEWLIST = URL + "news/getNewsWithAuthor";
//    public static String URL_NEWS_GOODS = URL + "news/goodNews";// 新闻点赞
    // 见地
    public static String URL_HOT_INSIGNLIST = URL + "news/getHotInsign";
    public static String URL_INSIGNLIST = URL + "news/getInsignList";
    //数据酷
    public static String URL_NEWS_GETDATASNEWS = URL + "news/getDataNews";
    public static String URL_GET_HOTGAME = URL + "game/getHotGame";
    public static String URL_GET_OTHERLIST = URL + "other/getOtherList";
    public static String URL_GET_OTHE_BY_ID = URL + "other/getOtherById";
    public static String URL_GETGAMEINFO_BYID = URL + "game/getGameById";
    public static String URL_GAMELIST = URL + "game/getGameList";
    public static String URL_HOTGAMELIST = URL + "game/getHotGame";
    public static String URL_ORDER_GAME_LIST = URL + "game/getOrderGameList";
    // 比赛
    public static String URL_HOT_GAME_LIST = URL + "contest/getHotContest";
    public static String URL_GET_CONTEST_LIST = URL + "contest/getContestList";// 比赛列表
    public static String URL_GET_INTEREST_CONTEST = URL + "contest/getInterestContest";// 关注比赛列表
    public static String URL_GET_GAME_INFO_BYID = URL + "contest/getContestById";// 关注比赛列表
    // 评论
    public static String URL_GET_COMMENT_BY_NEWS = URL + "comment/getCommentByNews";
    public static String URL_ADD_COMMENT = URL + "comment/addComment";// 增加评论
    public static String URL_COMMENT_ZAN = URL + "comment/goodComment";
    public static String URL_COMMENT_UNZAN = URL + "comment/ungoodComment";
    public static String URL_ATTENTION_DATA = URL + "athlete/InterestData";
    public static String URL_UNATTENTION_DATA = URL + "athlete/UnInterestData";
    public static String URL_USER_INTEREST_DATA = URL + "athlete/getUserInterestData";
    public static String URL_SEARCH_COUNTRY_TEAM = URL +"country/SearchCountryTeam";
    public static String URL_COLLECT_COMMENT= URL +"comment/InterestComment";// 收藏评论
    public static String URL_USER_COLLECT_COMMENT_LIST= URL +"comment/getUserInterestComment";// 用户收藏评论列表
    public static String URL_COMMENT_ALARM = URL +"comment/alarmComment";// 评论报警
    public static String URL_COMMENT_LIST_BY_USER = URL +"comment/getCommentByUser";// 用户评论列表

    //video
    public static String URL_VIDEO_LIST = URL +"vedio/getVedios";


    // 注入
    public static boolean AUTO_INJECT = true;
    // 状态是正常的
    public static int OK = 2000000;
    // 无登录状态异常
    public static int ERROR_LOGON_STATE = 5000054;

    public static String KEY_BOOL = "key-bool";
    public static String KEY_OBJ = "key-obj";
    public static String KEY_STR = "key-str";
    public static String KEY_STR2 = "key-str2";
    public static String KEY_ACTION = "key-action";
    public static String KEY_ID = "key-id";
    public static String KEY_INT = "key-int";
    public static String KEY_SERIALIZABLE = "key-serializable";

}
