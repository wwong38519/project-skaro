package project.skaro.expose.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @version $Id$
 */
public class WebClient {

//    private static final Logger logger = Logger.getLogger(WebClient.class);
    
    public HttpRequestBase getRequest(String url, String method) throws UnsupportedEncodingException {
        return getRequest(url, method, null, null);
    }
    
    public HttpRequestBase getRequest(String url, String method, List<NameValuePair> headers) throws UnsupportedEncodingException {
        return getRequest(url, method, headers, null);
    }
    
    public HttpRequestBase getRequest(String url, String method, List<NameValuePair> headers, List<NameValuePair> params) throws UnsupportedEncodingException {
        HttpRequestBase request = null;
        if (POST.equals(method)) {
            request = new HttpPost(url);
            if (params != null) ((HttpPost) request).setEntity(new UrlEncodedFormEntity(params));
        } else
        if (GET.equals(method)) {
            request = new HttpGet(url);
        }
        if (headers != null)
            for (NameValuePair header : headers)
                request.addHeader(header.getName(), header.getValue());
        return request;
    }
    
    public void call(HttpRequestBase request) throws ClientProtocolException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
//        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
//        request.setConfig(config);
        HttpResponse response = client.execute(request);
        InputStream is = response.getEntity().getContent();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        
        result = new Result();
        result.setStatusCode(response.getStatusLine().getStatusCode());
        result.setEntity(sb.toString());
    }
    
    public int getResponseCode() throws IOException {
        return result.getStatusCode();
    }
    
    public String getEntity() throws IOException {
        return result.getEntity();
    }
    
    public static String POST = "POST";
    public static String GET = "GET";
    private Result result;
        
    private class Result {
        private int statusCode;
        private String entity;
        
        public int getStatusCode() {
            return statusCode;
        }
        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }
        public String getEntity() {
            return entity;
        }
        public void setEntity(String entity) {
            this.entity = entity;
        }
    }
}
