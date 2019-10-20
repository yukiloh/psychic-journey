package xyz.murasakichigo.community.provider;


import com.alibaba.fastjson.JSON;
import xyz.murasakichigo.community.model.AccessToken;
import xyz.murasakichigo.community.model.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/*交由spring管理*/
@Component
public class GithubProvider {

    /*用于获取github提供的accessToken*/
    public String getAccessToken(AccessToken accessTokenDTO){
        /*使用okHttp快速解析获取的json*/
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        /*通过传入：AuthorizeController下的callback方法，获取的accessTokenDTO，生成RequestBody*/
//        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        RequestBody body = FormBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
        /*传入url&requestBody，获取request*/
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        /*传入request，获取response；此处为了打印获取的string所以没有直接return*/
        try (Response response = client.newCall(request).execute()) {
            /*获取body中的access_token  参考:31ed8bab88c1d796bdbb7e37cc12f016515bedf8&scope=&token_type=bearer*/
            String string = Objects.requireNonNull(response.body()).string();

            String[] split = string.split("=");
            String[] split1 = split[1].split("&");

            return split1[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*获取User*/
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = Objects.requireNonNull(response.body()).string();
            return JSON.parseObject(string, GithubUser.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;



    }

}
