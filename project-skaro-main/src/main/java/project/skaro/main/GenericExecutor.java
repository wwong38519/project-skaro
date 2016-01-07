package project.skaro.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import project.skaro.common.util.Constants;
import project.skaro.common.util.ExecUtils;
import project.skaro.common.util.PropertyUtils;

public class GenericExecutor implements Constants {

    public static void main(String[] args) {
        try {
            GenericExecutor.class.newInstance().execute(args);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void execute(String[] args) throws IOException {
        if (args == null || args.length < 1) 
            execute();
        else
            execute(args[0]);
    }

    public void execute() throws IOException {
        execute(file);
    }

    public void execute(String s) throws IOException {
        read(s);
    }

    protected void read(String file) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(new File(file)));
        String s;
        while ((s = r.readLine()) != null) {
            process(s);
        }
        r.close();
    }
    
    protected void process(String s) {
        if (StringUtils.isBlank(s) || s.startsWith(COMMENT)) return;
        String[] params = s.split(SPACE), args = null;
        if (params == null || params.length < 1) return;
        if (params.length > 1) {
            args = new String[params.length - 1];
            System.arraycopy(params, 1, args, 0, params.length - 1);
        }
        invoke(params[0], args);
    }
    
    protected void invoke(String className, String[] args) {
        ExecUtils.invoke(className, METHOD, String[].class, args);
    }
    
    private static String file = null;
    static {
        PropertyUtils prop = PropertyUtils.getInstance();
        file = prop.get(Constants.KEY_INPUT_PATH);
        if (file == null) file = "C://home//param.txt";
    }
    protected static final String METHOD = "execute";
}
