package project.skaro.expose.rdt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;

import project.skaro.common.main.Executable;
import project.skaro.expose.common.CommonCaller;

/**
 * @version $Id$
 */
public abstract class AbstractRdtParser extends CommonCaller implements Executable {

    public void execute(String[] args) {
        if (args == null || args.length != 2) return;
        execute(args[0], args[1]);
    }
    
    public void execute(String act, String arg) {
        try {
            if (ACT_SUB.equals(act)) sub(arg);
            if (ACT_CMT.equals(act)) comment(arg);
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
    
    protected static String getSubUrl(String sub) {
        return "http://www.reddit.com/r/"+sub+"/.json";
    }
    protected static String getCommentUrl(String id) {
        return "http://www.reddit.com/comments/"+id+"/.json";
    }
    
    public void sub(String sub) throws ClientProtocolException, UnsupportedEncodingException, IOException {
        parseSub(call(AbstractRdtParser.getSubUrl(sub)));
    }
    
    public void comment(String id) throws ClientProtocolException, UnsupportedEncodingException, IOException {
        parseComments(call(AbstractRdtParser.getCommentUrl(id)));
    }
    
    abstract protected void parseSub(String s);
    
    abstract protected void parseComments(String s);
    
    protected static final String ACT_SUB = "sub";
    protected static final String ACT_CMT = "comment";
}
