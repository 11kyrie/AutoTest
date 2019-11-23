package com.course.cases;

import com.course.config.TestConfig;
import com.course.config.UserInfor;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.http.client.methods.HttpPost;


import java.io.IOException;

public class LoginTest {

    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取HttpClient对象")
    public void beforeTest() {
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }

    @Test(groups = "loginTrue", description = "用户登陆成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);


        //第一步就是发送请求
        String result = getResult(loginCase);

        System.out.println(result);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");
        String token = obj.getJSONObject("data").getString("token");
        UserInfor.TOKEN = token;

        //类型转换
        //String s = String.valueOf(success);


        //验证结果
        Assert.assertEquals(loginCase.getExpected(), success);

    }


    @Test(groups = "loginFalse", description = "用户登陆失败接口测试")
    public void loginFalse() throws IOException {
        //for (int i = 2; i < 8; i++) {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);


        //第一步就是发送请求
        String result = getResult(loginCase);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");

        //类型转换
        //String s = String.valueOf(success);

        //验证结果
        Assert.assertEquals(loginCase.getExpected(), success);

    }


    @Test(groups = "case_03", description = "手机号码为空")
    public void case_03() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 3);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);


        //第一步就是发送请求
        String result = getResult(loginCase);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");
        String msg = obj.getString("err_msg");
        //类型转换
        //String s = String.valueOf(success);

        //验证结果
        Assert.assertEquals(loginCase.getExpected(), success);
        Assert.assertEquals(loginCase.getMsg(), msg);

    }

    @Test(groups = "case_04", description = "手机号码为空")
    public void case_04() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 4);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);


        //第一步就是发送请求
        String result = getResult(loginCase);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");
        String msg = obj.getString("err_msg");
        //类型转换
        //String s = String.valueOf(success);

        //验证结果
        Assert.assertEquals(loginCase.getExpected(), success);
        Assert.assertEquals(loginCase.getMsg(), msg);

    }

    @Test(groups = "case_05", description = "验证码为空")
    public void case_05() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 5);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);


        //第一步就是发送请求
        String result = getResult(loginCase);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");
        String msg = obj.getString("err_msg");
        //类型转换
        //String s = String.valueOf(success);

        //验证结果
        Assert.assertEquals(loginCase.getExpected(), success);
        Assert.assertEquals(loginCase.getMsg(), msg);

    }

    @Test(groups = "case_06", description = "输入的密码不正确")
    public void case_06() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 6);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);


        //第一步就是发送请求
        String result = getResult(loginCase);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");
        String msg = obj.getString("err_msg");
        //类型转换
        //String s = String.valueOf(success);

        //验证结果
        Assert.assertEquals(loginCase.getExpected(), success);
        Assert.assertEquals(loginCase.getMsg(), msg);

    }

    @Test(groups = "case_07", description = "为注册的手机号")
    public void case_07() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 7);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);


        //第一步就是发送请求
        String result = getResult(loginCase);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");
        String msg = obj.getString("err_msg");
        //类型转换
        //String s = String.valueOf(success);

        //验证结果
        Assert.assertEquals(loginCase.getExpected(), success);
        Assert.assertEquals(loginCase.getMsg(), msg);

    }


    private String getResult(LoginCase loginCase) throws IOException {

        HttpPost post = new HttpPost(TestConfig.loginUrl);

        JSONObject param = new JSONObject();
        param.put("tel", loginCase.getTel());
        param.put("code", loginCase.getCode());
//
        post.setHeader("content-type", "application/json");

        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);

        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");

        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
        return  result;
//        String result = ApiRequest.getApi(TestConfig.loginUrl, param.toString());
//        return result;
    }


}
