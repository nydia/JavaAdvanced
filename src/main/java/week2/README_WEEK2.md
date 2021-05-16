# 第二周作业

#### 作业4： 

```text

GC现在写总结的话目前领悟到的还是老师课上的总结，深入层次的我想在视频多听几次之后在请教老师，谢谢。

```



#### 作业6： JavaAdvanced\src\main\java\week2\task6\HttpReqDemo.java

只复制了客户端请求的代码，服务端的代码没有黏贴进来。

```java

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


```