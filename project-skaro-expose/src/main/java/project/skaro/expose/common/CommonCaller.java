package project.skaro.expose.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

/**
 * @version $Id$
 */
public class CommonCaller {

    protected static final Logger logger = Logger.getLogger(CommonCaller.class);

    protected static String method = WebClient.GET;
    protected static List<NameValuePair> headers = null;
    protected static List<NameValuePair> params = null;
    
    protected WebClient client;
    
    public CommonCaller() {
        init();
    }
    
    protected void init() {
        client = new WebClient();
        addHeader("User-agent", getRandomUA());
    }
        
    protected String call(String url) throws IOException {
        client.call(client.getRequest(url, method, headers, params));
//        logger.debug(client.getResponseCode());
//        logger.debug(client.getEntity());
        return client.getEntity();
    }
    
    protected void addHeader(String key, String value) {
        if (headers == null) headers = new ArrayList<NameValuePair>();
        headers.add(new BasicNameValuePair(key, value));
    }
    
    protected void addParams(String key, String value) {
        if (params == null) params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(key, value));
    }
    
    private String getRandomUA() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) sb.append(r.nextInt(10));
        return sb.toString();
    }

}
