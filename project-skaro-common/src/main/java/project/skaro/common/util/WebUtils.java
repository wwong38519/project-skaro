package project.skaro.common.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class WebUtils implements Constants {
    
//    private static Logger logger = Logger.getLogger(WebUtils.class);

    public static Map<String, String> parseLink(String link) {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        try {
            URL url = new URL(link);
            String query = url.getQuery();
            String[] pairs = query.split(AMP);
            for (String pair : pairs) {
                int idx = pair.indexOf(EQ);
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), UTF_8), URLDecoder.decode(pair.substring(idx + 1), UTF_8));
            }
        }
        catch (MalformedURLException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return query_pairs;
    }
    
}
