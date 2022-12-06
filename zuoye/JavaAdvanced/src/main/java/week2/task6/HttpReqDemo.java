package week2.task6;


import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @Auther: hqlv
 * @Description: 第二周作业6
 */
public class HttpReqDemo {

    public static void main(String[] args) {
        httpClint();
    }

    public static void httpClint(){

        String url = "http://localhost:8081";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
