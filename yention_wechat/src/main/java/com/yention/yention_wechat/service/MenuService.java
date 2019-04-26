package com.yention.yention_wechat.service;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2016/11/8.
 */
public interface MenuService {
    public JSONObject getMenu(String accessToken);
    public int createMenu(String accessToken);
    public int deleteMenu(String accessToken);

}
