package com.course.cases;

import com.course.config.TestConfig;
import com.course.config.UserInfor;
import com.course.model.GetIndexCase;
import com.course.model.InterfaceName;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetIndexTest {
    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取HttpClient对象")
    public void beforeTest() {
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.getSmsCodeUrl = ConfigFile.getUrl(InterfaceName.GETSMSCODE);
        TestConfig.getIndexUrl = ConfigFile.getUrl(InterfaceName.GETINDEX);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }

    @Test//(groups = "loginTrue", description = "获取login短信验证码成功")
    public void getIndex() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetIndexCase getIndexCase = session.selectOne("getIndexCase", 1);
        System.out.println(getIndexCase.toString());
        System.out.println(TestConfig.getIndexUrl);

        //第一步就是发送请求
        String result = getResult(getIndexCase);
        System.out.println(result);

        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");
        //验证结果

        //String s = String.valueOf(success);

        Assert.assertEquals(getIndexCase.getExpected(), success);

    }


    private String getResult(GetIndexCase getIndexCase) throws IOException {
        HttpGet httpGet = new HttpGet(TestConfig.getIndexUrl); //创建一个get请求并发送参数
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


        //httpGet.setHeader()
//	httpGet.setHeader("Accept", "application/json");
//
//	httpGet.setHeader("Accept-Encoding", "gzip, deflate");
//	httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");//设置http头部信息
//
//    RequestConfig config = RequestConfig.custom() //RequestConfig.Builder 配置器 ，我们可以通过custom获取一个新的Builder对象
//            .setConnectTimeout(30000)  		//设置链接超时的时间1秒钟
//            .setSocketTimeout(30000) 		//设置读取超时1秒钟
//            .build(); 						//RequestConfig静态方法  setProxy  设置代理
//	 httpGet.setConfig(config);//设置头部信息
//
//    CloseableHttpResponse execute=null;//实例话对象赋值

    }
}
