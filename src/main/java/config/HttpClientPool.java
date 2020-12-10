package config;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.net.URLDecoder;

/**
 * @author : Roc
 * @date : 2020-12-07 11:00
 * @description :
 **/
public enum HttpClientPool {
    /**
     * 实现单例
     */
    INSTANCE;

    /**
     * 连接池管理员
     */
    private PoolingHttpClientConnectionManager connectionManager;

    HttpClientPool() {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(20);
        connectionManager.setDefaultMaxPerRoute(100);
    }

    private CloseableHttpClient getHttpClient() {
        return getHttpClient(3000, 3000);
    }

    private CloseableHttpClient getHttpClient(int connectionTimeout, int socketTimeOut) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionTimeout).setSocketTimeout(socketTimeOut).build();
        return HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();
    }

    public String get(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        return getResponseContent(url, httpGet);
    }

    public String post(String url) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        return getResponseContent(url, httpPost);
    }

    public String postJson(String url, String json) throws Exception {
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        return getResponseContent(url, post);
    }

    private String getResponseContent(String url, HttpRequestBase request) throws Exception {
        HttpResponse response = null;
        try {
            //HttpClient连接交给连接池管理后则不能再做关闭等操作干预
            response = this.getHttpClient().execute(request);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            throw new Exception("got an error from HTTP for url : " + URLDecoder.decode(url, "UTF-8"), e);
        } finally {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
            request.releaseConnection();
        }
    }
}
