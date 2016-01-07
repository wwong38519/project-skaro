package project.skaro.expose.rdb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;

import project.skaro.common.main.Executable;
import project.skaro.common.util.Constants;
import project.skaro.common.util.PropertyUtils;
import project.skaro.expose.common.CommonCaller;
import project.skaro.expose.rdb.domain.RdbJson;

import com.google.gson.Gson;

/**
 * @version $Id$
 */
public class Readb extends CommonCaller implements Executable {

    private static String TOKEN = null;
    static {
        PropertyUtils prop = PropertyUtils.getInstance();
        TOKEN = prop.get(Constants.KEY_RDB_TOKEN);
    }

    public void execute(String[] args) {
        for (String arg : args) execute(arg);
    }
    
    public void execute(String arg) {
        new Readb().read(arg);
    }
    
    public static String getCallUrl(String url) {
        return "https://www.readability.com/api/content/v1/parser?url="+url+"&token="+TOKEN;
    }
     
    public void read(String url) {
        try {
            present(parse(call(Readb.getCallUrl(url))));
        }
        catch (ClientProtocolException ex) {
            ex.printStackTrace();
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public RdbJson parse(String s) {
        return new Gson().fromJson(s, RdbJson.class);
    }
    
    private void present(RdbJson s) {
        Display.console(s.getTitle());
        Display.console(s.getContent());
//        Display.console(s.getExcerpt());
    }
   
    
}
