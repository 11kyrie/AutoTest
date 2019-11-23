package com.course.cases;

import com.course.config.TestConfig;
import com.course.config.UserInfor;
import com.course.config.UserSms;
import com.course.model.GetSmsCodeCase;
import com.course.model.InterfaceName;
import com.course.model.VerifySmsCheckCase;
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

public class VerifySmsCheckTest {
    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取HttpClient对象")
    public void beforeTest() {
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.getSmsCodeUrl = ConfigFile.getUrl(InterfaceName.GETSMSCODE);
        TestConfig.verifySmsCheckUrl = ConfigFile.getUrl(InterfaceName.VERIFYSMSCHECK);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }
    @Test(groups = "sendTrue", description = "获取login短信验证码成功")
    public void verifySmsCheckTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        VerifySmsCheckCase verifySmsCheckCase = session.selectOne("verifySmsCheckCase", 1);
        System.out.println(verifySmsCheckCase.toString());
        System.out.println(TestConfig.verifySmsCheckUrl);

        //第一步就是发送请求
        String result = getResult(verifySmsCheckCase);
        System.out.println(result);

        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");
        //验证结果

        //String s = String.valueOf(success);

        Assert.assertEquals(verifySmsCheckCase.getExpected(), success);

    }


    @Test(groups = "sendFalse",description = "获取短信验证码失败")
    public void verifySmsCheckFalse() throws IOException{
        SqlSession session = DatabaseUtil.getSqlSession();
        VerifySmsCheckCase verifySmsCheckCase = session.selectOne("verifySmsCheckCase",2);
        System.out.println(verifySmsCheckCase.toString());
        System.out.println(TestConfig.verifySmsCheckUrl);


        //第一步就是发送请求
        String result = getResult(verifySmsCheckCase);
        System.out.println(result);

        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");

        Assert.assertEquals(verifySmsCheckCase.getExpected(),success);

    }


    private String getResult(VerifySmsCheckCase  verifySmsCheckCase) throws IOException {
        System.out.println("verifySmsCheckUrl"+TestConfig.verifySmsCheckUrl);
        HttpPost post = new HttpPost(TestConfig.verifySmsCheckUrl);
        JSONObject param = new JSONObject();
        param.put("tel", verifySmsCheckCase.getTel());
        param.put("type", verifySmsCheckCase.getType());
        param.put("code",UserSms.Sms);
        System.out.println("sms"+UserSms.Sms);
        //System.out.println(JSONObject.valueToString(param));
        post.setHeader("content-type", "application/json");
        post.setHeader("Authorization", UserInfor.TOKEN);


        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);

        String result;


        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");

        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
        return  result;
    }
}
