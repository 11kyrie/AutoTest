package com.course.cases;

import com.course.config.TestConfig;
import com.course.config.UserInfor;
import com.course.model.HelpFeedbackCase;
import com.course.model.InterfaceName;
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

public class HelpFeedbackTest {

    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取HttpClient对象")
    public void beforeTest() {
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.getSmsCodeUrl = ConfigFile.getUrl(InterfaceName.GETSMSCODE);
        TestConfig.helpAdviseUrl = ConfigFile.getUrl(InterfaceName.HELPADVISE);
        TestConfig.helpFeedbackUrl = ConfigFile.getUrl(InterfaceName.HELPFEEDBACK);


        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }


    @Test//(dependsOnGroups = {"loginTrue"},description = "反馈和建议")
    public void helpFeedbackTrue() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        HelpFeedbackCase helpFeedbackCase = session.selectOne("helpFeedbackCase", 1);
        System.out.println(helpFeedbackCase.toString());
        System.out.println(TestConfig.helpFeedbackUrl);

        String result = getResult(helpFeedbackCase);
        System.out.println(result);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");

        //类型转换
        //String s = String.valueOf(success);

        //验证结果
        Assert.assertEquals(helpFeedbackCase.getExpected(), success);
    }

    @Test//(dependsOnGroups = {"loginTrue"},description = "反馈和建议")
    public void helpFeedbackFalse() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        HelpFeedbackCase helpFeedbackCase = session.selectOne("helpFeedbackCase",2);
        System.out.println(helpFeedbackCase.toString());
        System.out.println(TestConfig.helpFeedbackUrl);

        String result = getResult(helpFeedbackCase);
        System.out.println(result);

        //解析json获取要验证的字段的值
        JSONObject obj = new JSONObject(result);
        Boolean success = obj.getBoolean("success");

        //类型转换
        //String s = String.valueOf(success);

        //验证结果
        Assert.assertEquals(helpFeedbackCase.getExpected(),success);
    }

    private String getResult(HelpFeedbackCase helpFeedbackCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.helpFeedbackUrl);
        JSONObject param = new JSONObject();
        param.put("content", helpFeedbackCase.getContent());
        param.put("images", helpFeedbackCase.getImages());
        param.put("tel", helpFeedbackCase.getTel());

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