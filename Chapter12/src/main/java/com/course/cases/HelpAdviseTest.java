package com.course.cases;

import com.course.config.TestConfig;
import com.course.config.UserInfor;
import com.course.model.HelpAdviseCase;
import com.course.model.InterfaceName;
import com.course.model.UpdateUserInfoCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class HelpAdviseTest {

    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取HttpClient对象")
    public void beforeTest() {
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.getSmsCodeUrl = ConfigFile.getUrl(InterfaceName.GETSMSCODE);
        TestConfig.helpAdviseUrl = ConfigFile.getUrl(InterfaceName.HELPADVISE);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }


    @Test//(dependsOnGroups = {"loginTrue"},description = "反馈和建议")
    public void helpAdviseTrue() throws IOException, InterruptedException {
            SqlSession session = DatabaseUtil.getSqlSession();
            HelpAdviseCase helpAdviseCase = session.selectOne("helpAdviseCase", 1);
            System.out.println(helpAdviseCase.toString());
            System.out.println(TestConfig.helpAdviseUrl);

            String result = getResult(helpAdviseCase);
            System.out.println(result);

            //解析json获取要验证的字段的值
            JSONObject obj = new JSONObject(result);
            Boolean success = obj.getBoolean("success");

            //类型转换
            //String s = String.valueOf(success);

            //验证结果
            Assert.assertEquals(helpAdviseCase.getExpected(), success);
        }

    @Test//(dependsOnGroups = {"loginTrue"},description = "反馈和建议")
    public void helpAdviseFalse() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        HelpAdviseCase helpAdviseCase = session.selectOne("helpAdviseCase",1);
        System.out.println(helpAdviseCase.toString());
        System.out.println(TestConfig.helpAdviseUrl);

        String result = getResult(helpAdviseCase);
        System.out.println(result);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");

        //类型转换
        //String s = String.valueOf(success);

        //验证结果
        Assert.assertEquals(helpAdviseCase.getExpected(),success);
    }

    private String getResult(HelpAdviseCase helpAdviseCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.helpAdviseUrl);
        JSONObject param = new JSONObject();
        param.put("content", helpAdviseCase.getContent());
        param.put("images", helpAdviseCase.getImages());
        param.put("tel", helpAdviseCase.getTel());

        post.setHeader("content-type", "application/json");
//        if(TextUtils.isEmpty(UserInfor.TOKEN)){
//            post.setHeader("Authorization","d7145a1592828406aec9e64d865d8927");
//        }else {
        post.setHeader("Authorization", UserInfor.TOKEN);
        //}

        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);

        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");

        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
        return result;


//        String result = ApiRequest.getApi(TestConfig.helpAdviseUrl, param.toString());
//        return  result;
    }

}
