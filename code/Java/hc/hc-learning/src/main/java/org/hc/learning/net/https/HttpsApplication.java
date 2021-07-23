package org.hc.learning.net.https;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpsApplication {

    public static void main(String[] args) throws IOException {
        /*SSLContext context = SSLContexts.createSystemDefault();*/
        /*SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context);*/

        CloseableHttpClient client = HttpClients.custom()/*.setSSLContext(context)*/.build();
        HttpPost post = new HttpPost("https://tc.cloud-dahua.com/gateway/auth/oauth/token");
        RequestConfig config = RequestConfig.DEFAULT;
        post.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
        post.setConfig(config);

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("client_id", "reb1d80e5931444e57a75e42e32fe09525"));
        list.add(new BasicNameValuePair("client_secret", "ce514527e7d2620bc92905c8bb7f57c9"));
        list.add(new BasicNameValuePair("grant_type", "client_credentials"));
        list.add(new BasicNameValuePair("scope", "server"));
        HttpEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
        post.setEntity(entity);
        CloseableHttpResponse response = client.execute(post);
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

}
