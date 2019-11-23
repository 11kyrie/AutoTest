package com.course.cases;

import com.course.config.ApiConfig;
import com.course.config.TestConfig;
import com.course.config.UserInfor;
import com.course.model.GetFadCase;
import com.course.model.GetIndexCase;
import com.course.model.InterfaceName;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.course.config.TestConfig.getFadUrl;

public class GetFadTest {
//    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取HttpClient对象")
//    public void beforeTest() {
//        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
//        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
//        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
//        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
//        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
//        TestConfig.getSmsCodeUrl = ConfigFile.getUrl(InterfaceName.GETSMSCODE);
//        TestConfig.getIndexUrl = ConfigFile.getUrl(InterfaceName.GETINDEX);
//
//        TestConfig.defaultHttpClient = new DefaultHttpClient();
//    }

    @Test//(groups = "loginTrue", description = "获取fad列表详情")
    public void getFad() throws IOException,URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetFadCase getFadCase = session.selectOne("getFadCase", 1);
        System.out.println(getFadCase.toString());
        //System.out.println();

        //第一步就是发送请求
        String result = getResult(getFadCase);
        System.out.println(result);

        JSONObject obj = new JSONObject(result);
        boolean success = obj.getBoolean("success");
        //验证结果

        //String s = String.valueOf(success);
        Assert.assertEquals(false,success);

    }



    private String getResult(GetFadCase getFadCase) throws IOException, URISyntaxException {
//        HttpGet httpGet = new HttpGet(TestConfig.getIndexUrl); //创建一个get请求并发送参数
//        JSONObject params = new JSONObject();
//        params.put("idd", getFadCase.getIdd());
//
////        String queryString = URLEncodedUtils.format(params, "utf-8");
//        httpGet.setHeader("content-type", "application/json");
//        httpGet.setHeader("Authorization", UserInfor.TOKEN);
//
//        RequestConfig config = RequestConfig.custom() //RequestConfig.Builder 配置器 ，我们可以通过custom获取一个新的Builder对象
//                .setConnectTimeout(30000)        //设置链接超时的时间1秒钟
//                .setSocketTimeout(30000)        //设置读取超时1秒钟
//                .build();                        //RequestConfig静态方法  setProxy  设置代理
//        httpGet.setConfig(config);
//        String result;
//        HttpResponse response = TestConfig.defaultHttpClient.execute(httpGet);
//
//        result = EntityUtils.toString(response.getEntity(), "utf-8");
//
//        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
//        return result;
////        // (1)创建查询参数
////        List<NameValuePair> params = new ArrayList<NameValuePair>();
////        params.add(new BasicNameValuePair("idd",getFadCase.getIdd()));
////        params.add(new BasicNameValuePair("work", "程序员"));
////        String queryString = URLEncodedUtils.format(params, "utf-8");
////
////// (2) 创建Get实例
//        URI uri = URIUtils.createURI( "http",8080, getFadUrl, queryString, null);
//
////        HttpGet get = new HttpGet(uri);
        // (1)创建查询参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id",getFadCase.getIdd()+""));
        String queryString = URLEncodedUtils.format(params, "utf-8");

// (2) 创建Get实例
        URI uri = URIUtils.createURI("http", ApiConfig.BASE_URL, 80,ApiConfig.servlet, queryString, null);
        HttpGet httpGet = new HttpGet(uri);
        System.out.println(uri.toString());

        httpGet.setHeader("content-type", "application/json");
        httpGet.setHeader("Authorization", UserInfor.TOKEN);

        RequestConfig config = RequestConfig.custom() //RequestConfig.Builder 配置器 ，我们可以通过custom获取一个新的Builder对象
                .setConnectTimeout(30000)        //设置链接超时的时间1秒钟
                .setSocketTimeout(30000)        //设置读取超时1秒钟
                .build();                        //RequestConfig静态方法  setProxy  设置代理
        httpGet.setConfig(config);
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(httpGet);

        result = EntityUtils.toString(response.getEntity(), "utf-8");

        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
        return result;

    }
}
