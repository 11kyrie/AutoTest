//package com.course.cases;
//
//import com.course.config.TestConfig;
//import com.course.config.UserInfor;
//import com.course.model.LoginCase;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//public class ApiRequest {
//
//    public static String getApi(String url, String entiyString) throws IOException {
//        String result = "";
//        HttpPost post = new HttpPost(url);
//        post.setHeader("content-type", "application/json");
//     //   post.setHeader("Authorization", UserInfor.TOKEN);
//        StringEntity entity = new StringEntity(entiyString, "utf-8");
//        post.setEntity(entity);
//        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
//        result = EntityUtils.toString(response.getEntity(), "utf-8");
//        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
//        return result;
//    }
//}
