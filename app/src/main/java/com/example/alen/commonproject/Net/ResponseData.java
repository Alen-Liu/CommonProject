package com.example.alen.commonproject.Net;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alen on 2017/2/11.
 */
public class ResponseData {
    public static String CODE_OK = "1";
    public static String CODE_FAIL = "0";
    public static String CODE_FROZEN = "3";
    public static String CODE_NULL_DATA = "-1";
    public static String CODE_NO_USER = "4";
    public static String CODE_NOT_LOGIN = "2";
    public String mCode;
    public String mMsg;
    public JSONObject mObject;

    public ResponseData(String data) {
        try {
            mObject = new JSONObject(data);//应该是response
            mCode = mObject.getString("code");
            mMsg = mObject.getString("msg");
        } catch (JSONException e) {

            e.printStackTrace();
        }

    }
}
