package com.course.config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestConfig {
    public static String loginUrl;
    public static String updateUserInfoUrl;
    public static String getUserListUrl;
    public static String getUserInfoUrl;
    public static String addUserUrl;
    public static String getSmsCodeUrl;
    public static String helpAdviseUrl;
    public static String getIndexUrl;
    public static String getFadUrl;
    public static String verifySmsCheckUrl;
    public static String helpFeedbackUrl;



    public static DefaultHttpClient defaultHttpClient;
    public static CookieStore store;
}
