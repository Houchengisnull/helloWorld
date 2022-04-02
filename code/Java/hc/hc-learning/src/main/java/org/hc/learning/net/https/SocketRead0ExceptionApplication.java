package org.hc.learning.net.https;

import lombok.SneakyThrows;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketRead0ExceptionApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        RequestConfig config = RequestConfig.custom()
                /*.setSocketTimeout(1000)
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(1000)*/
                .build();
        CloseableHttpClient build = HttpClients.custom().setDefaultRequestConfig(config).build();
        while (true) {
            for (int i = 0; i < 10; i++) {
                service.execute(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        HttpGet httpGet = new HttpGet("http://172.18.21.65:8090/hc-web/test/");
                        CloseableHttpResponse response = build.execute(httpGet);
                        System.out.println(response.getStatusLine());
                        String content = EntityUtils.toString(response.getEntity());
                        System.out.println(content);
                    }
                });
            }
            Thread.sleep(100l);
        }
    }
}
