package com.course.utils;

import com.course.model.InterfaceName;

import java.net.URI;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {

    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(InterfaceName name){
        String address = bundle.getString("test.url");
        String uri="";
        //最终的测试地址
        String testUrl;

//        if(name == InterfaceName.GETUSERLIST){
//            uri = bundle.getString("getUserList.uri");
//        }

        if(name == InterfaceName.LOGIN){
            uri = bundle.getString("login.uri");
        }

        if(name == InterfaceName.GETSMSCODE ){
            uri = bundle.getString("getSmsCode.uri");
        }
        if(name == InterfaceName.GETINDEX){
            uri = bundle.getString("getIndex.uri");
        }
        if(name == InterfaceName.HELPADVISE){
            uri = bundle.getString("helpAdvise.uri");
        }
        if(name == InterfaceName.GETFAD){
            uri = bundle.getString("helpDetail.uri");
        }
        if(name == InterfaceName.VERIFYSMSCHECK){
            uri = bundle.getString("verifySmsCheck.uri");
        }
        if(name == InterfaceName.HELPFEEDBACK){
            uri = bundle.getString("helpFeedback.uri");
        }




//
//        if(name == InterfaceName.GETUSERINFO){
//            uri = bundle.getString("getUserInfo.uri");
//        }
//
//        if(name == InterfaceName.ADDUSERINFO){
//            uri = bundle.getString("addUser.uri");
//        }

        testUrl = address + uri;
        return testUrl;
    }

}
