//package com.course.cases;
//
//import com.course.config.TestConfig;
//import com.course.model.LoginCase;
//import com.course.model.UpdateUserInfoCase;
////import com.course.model.User;
//import com.course.utils.DatabaseUtil;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.util.EntityUtils;
//import org.apache.ibatis.session.SqlSession;
//import org.json.JSONObject;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//
//public class UpdateUserInfoTest {
//
//
//    @Test(dependsOnGroups = {"loginTrue"},description = "删除用户信息")
//    public void updateUser_01() throws IOException, InterruptedException {
//        SqlSession session = DatabaseUtil.getSqlSession();
//        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);
//        System.out.println(updateUserInfoCase.toString());
//        System.out.println(TestConfig.updateUserInfoUrl);
//
//        String result = getResult(updateUserInfoCase);
//        System.out.println(result);
//
////        //解析json获取要验证的字段的值
////        JSONObject obj = new JSONObject(result);
////        Boolean success = obj.getBoolean("success");
////
////        //类型转换
////        //String s = String.valueOf(success);
////
////        //验证结果
////        Assert.assertEquals(updateUserInfoCase.getExpected(),success);
//    }
//
////    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息")
////    public void updateUser_02() throws IOException, InterruptedException {
////        SqlSession session = DatabaseUtil.getSqlSession();
////        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",2);
////        System.out.println(updateUserInfoCase.toString());
////        System.out.println(TestConfig.updateUserInfoUrl);
//
////        String result = getResult(updateUserInfoCase);
//////        System.out.println(result);
////
////        //解析json获取要验证的字段的值
////        JSONObject obj = new JSONObject(result);
////        Boolean success = obj.getBoolean("success");
////
////        //类型转换
////        //String s = String.valueOf(success);
////
////        //验证结果
////        Assert.assertEquals(updateUserInfoCase.getExpected(),success);
////    }
//
//    private String getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException, InterruptedException {
//        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
//        JSONObject param = new JSONObject();
//        param.put("id",updateUserInfoCase.getId());
//        param.put("avatar",updateUserInfoCase.getAvatar());
//        param.put("realname",updateUserInfoCase.getRealname());
//        param.put("gender",updateUserInfoCase.getGender());
//        param.put("education",updateUserInfoCase.getEducation());
//        param.put("description",updateUserInfoCase.getDescription());
//        param.put("birthday",updateUserInfoCase.getBirthday());
//        param.put("age",updateUserInfoCase.getAge());
//        param.put("school_age",updateUserInfoCase.getSchool_age());
//        param.put("province",updateUserInfoCase.getProvince());
//        param.put("city",updateUserInfoCase.getCity());
//        param.put("area",updateUserInfoCase.getArea());
//
//
//        post.setHeader("content-type", "application/json");
//
//        StringEntity entity = new StringEntity(param.toString(), "utf-8");
//        post.setEntity(entity);
//
//        String result;
//        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
//        result = EntityUtils.toString(response.getEntity(), "utf-8");
//
//        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
//        return result;
//    }
//
//}
