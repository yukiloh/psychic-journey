package xyz.murasakichigo.community.provider;


import com.alibaba.fastjson.JSON;
import dto.AccessTokenDTO;
import dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String stirng = response.body().string();
            System.out.println(stirng);
            return stirng;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String stirng = response.body().string();
            GithubUser githubUser = JSON.parseObject(stirng, GithubUser.class); /*将获得的string自动解析为githubUser*/
            return githubUser;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;



    }
}
