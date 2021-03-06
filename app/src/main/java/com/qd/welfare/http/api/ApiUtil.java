package com.qd.welfare.http.api;

import com.qd.welfare.App;
import com.qd.welfare.utils.MD5Util;

import java.util.HashMap;


/**
 * Case By:API
 * package:wiki.scene.shop.http.api
 * Author：scene on 2017/6/27 12:51
 */

public class ApiUtil {
    private static final String SIGN_KEY = "045448f765b0c0592563123a2652fb63";
    public static final String API_PRE = "https://apis.goldenchild.cn";

    public static final String USER = "/user";
    public static final String USER_TAG = "USER_TAG";

    public static final String CATEGORY = "/gallery";
    public static final String CATEGORY_TAG = "CATEGORY_TAG";

    public static final String CATEGORY_ACTOR = "/gallery/actor";
    public static final String CATEGORY_ACTOR_TAG = "CATEGORY_ACTOR_TAG";

    public static final String COMMON = "/common";
    public static final String COMMON_TAG = "COMMON_TAG";

    public static final String ACTRESS = "/actor";
    public static final String ACTRESS_TAG = "ACTRESS_TAG";

    public static final String GALLERY = "/gallery/detail";
    public static final String GALLERY_TAG = "GALLERY_TAG";

    public static final String RECOMMEND = "/video/hot";
    public static final String RECOMMEND_TAG = "RECOMMEND_TAG";

    public static final String ACTOR_DETAIL = "/actor/detail";
    public static final String ACTOR_DETAIL_TAG = "ACTOR_DETAIL_TAG";

    public static final String VIDEO = "/video";
    public static final String VIDEO_TAG = "VIDEO_TAG";
    public static final String VIDEO1 = "/video/video1";
    public static final String VIDEO1_TAG = "VIDEO1_TAG";

    public static final String VIDEO_DETAIL = "/video/play";
    public static final String VIDEO_DETAIL_TAG = "VIDEO_DETAIL_TAG";

    public static final String UPLOAD_USER_INFO = "/data/stay";
    public static final String UPLOAD_USER_INFO_TAG = "UPLOAD_USER_INFO_TAG";

    public static final String UPLOAD_POSITION = "/data/position";
    public static final String UPLOAD_POSITION_TAG = "UPLOAD_POSITION_TAG";

    public static final String GET_PAY_INFO = "/pay_vip";
    public static final String GET_PAY_INFO_TAG = "GET_PAY_INFO_TAG";

    public static final String CHECK_ORDER_STATUS = "/pay_vip/is_success";
    public static final String ChECK_ORDER_GOODS_TAG = "ChECK_ORDER_GOODS_TAG";

    public static final String UPDATE_VERSION = "/version/android";
    public static final String UPDATE_VERSION_TAG = "UPDATE_VERSION_TAG";

    public static final String NOVEL_CATEGORY = "/novel/category";
    public static final String NOVEL_CATEGORY_TAG = "NOVEL_CATEGORY_TAG";

    public static final String NOVEL = "/novel";
    public static final String NOVEL_TAG = "NOVEL_TAG";

    public static final String NOVEL_CHAPTER = "/novel/chapter";
    public static final String NOVEL_CHAPTER_TAG = "NOVEL_CHAPTER_TAG";

    public static final String NOVEL_DETAIL = "/novel/detail";
    public static final String NOVEL_DETAIL_TAG = "NOVEL_DETAIL_TAG";

    public static final String GOODS_DETAIL = "/goods/detail";
    public static final String GOODS_DETAIL_TAG = "GOODS_DETAIL_TAG";

    public static final String CREATE_ORDER = "/order";
    public static final String CREATE_ORDER_TAG = "CREATE_ORDER_TAG";

    public static final String CHECK_GOODS_ORDER = "/order/is_success";
    public static final String CHECK_GOODS_ORDER_TAG = "CHECK_GOODS_ORDER_TAG";

    public static final String ORDER_LIST = "/order/lists";
    public static final String ORDER_LIST_TAG = "ORDER_LIST_TAG";

    public static final String ORDER_DETAIL = "/order/detail";
    public static final String ORDER_DETAIL_TAG = "ORDER_DETAIL_TAG";

    //获取物流信息
    public static final String GET_LOGISTICS = "http://www.kuaidi100.com/query";
    public static final String GET_LOGISTICS_TAG = "GET_LOGISTICS_TAG";
    //统计播放次数
    public static final String PLAY_COUNT = "/video/play_count";
    public static final String PLAY_COUNT_TAG = "PLAY_COUNT_TAG";
    //获取开通成功的用户数据
    public static final String GET_PAY_SUCCESS_INFO = "/user/open_vip";
    public static final String GET_PAY_SUCCESS_INFO_TAG = "GET_PAY_SUCCESS_INFO_TAG";
    //弹幕
    public static final String DANMU = "/video/danmu";
    public static final String DANMU_TAG = "DANMU_TAG";

    /**
     * Case By:创建参数基础信息
     * Author: scene on 2017/5/19 13:19
     */
    public static HashMap<String, String> createParams() {
        HashMap<String, String> params = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        params.put("agent_id", App.CHANNEL_ID);
        params.put("resource_id", App.RESOURCE_ID);
        params.put("timestamp", timestamp + "");
        params.put("signature", getSign(App.CHANNEL_ID, timestamp + ""));
        params.put("app_type", "1");
        params.put("uuid", App.UUID);
        params.put("version", App.versionCode + "");
        return params;
    }

    /**
     * Case By:获取sign
     * Author: scene on 2017/5/19 13:19
     */
    private static String getSign(String agent_id, String timestamp) {
        return MD5Util.string2Md5(MD5Util.string2Md5(agent_id + 1 + App.RESOURCE_ID + timestamp + App.UUID + App.versionCode, "UTF-8") + SIGN_KEY, "UTF-8");
    }
}
