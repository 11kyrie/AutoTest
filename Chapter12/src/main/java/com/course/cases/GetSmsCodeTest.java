package com.course.cases;

import com.course.config.TestConfig;
import com.course.config.UserInfor;
import com.course.config.UserSms;
import com.course.model.GetSmsCodeCase;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetSmsCodeTest {


    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取HttpClient对象")
    public void beforeTest() {
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.getSmsCodeUrl = ConfigFile.getUrl(InterfaceName.GETSMSCODE);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }
    @Test(groups = "sendTrue", description = "获取login短信验证码成功")
    public void sendTrue() throws IOException {
            SqlSession session = DatabaseUtil.getSqlSession();
            GetSmsCodeCase getSmsCodeCase = session.selectOne("getSmsCodeCase", 1);
            System.out.println(getSmsCodeCase.toString());
            System.out.println(TestConfig.getSmsCodeUrl);

            //第一步就是发送请求
            String result = getResult(getSmsCodeCase);
            System.out.println(result);

            JSONObject obj = new JSONObject(result);
            Boolean success = obj.getBoolean("success");
            //存验证码
            String sms = obj.getString("data");
            UserSms.Sms=sms;
            //验证结果

            //String s = String.valueOf(success);

            Assert.assertEquals(getSmsCodeCase.getExpected(), success);

        }
    @Test(groups = "sendFalse",description = "获取短信验证码失败")
    public void sendFalse() throws IOException{
        SqlSession session = DatabaseUtil.getSqlSession();
        GetSmsCodeCase getSmsCodeCase = session.selectOne("getSmsCodeCase",2);
        System.out.println(getSmsCodeCase.toString());
        System.out.println(TestConfig.getSmsCodeUrl);


        //第一步就是发送请求
        String result = getResult(getSmsCodeCase);
        System.out.println(result);

        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");

        Assert.assertEquals(getSmsCodeCase.getExpected(),success);

    }


    private String getResult(GetSmsCodeCase getSmsCodeCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getSmsCodeUrl);
        JSONObject param = new JSONObject();
        param.put("tel", getSmsCodeCase.getTel());
        param.put("type", getSmsCodeCase.getType());
        //System.out.println(JSONObject.valueToString(param));
        post.setHeader("content-type", "application/json");

        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);

        String result;


        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");

        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
        return  result;
    }
}
