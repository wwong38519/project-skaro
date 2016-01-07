package project.skaro.main;

import project.skaro.common.main.Executable;
import project.skaro.common.util.CommonUtils;



public class Executor extends GenericExecutor {

    public static void main(String[] args) {
        try {
            Executable.Display.clazz = CommonUtils.class;
            Executor.class.newInstance().execute(args);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // designated classes
    public void process(String s) {
        Class<?> clazz = super.getClass();
        String[] param = null;
        if (s.toLowerCase().startsWith("http")) {           // project.skaro.expose.rdb.Readb http:...
            clazz = project.skaro.expose.rdb.Readb.class;
            param = new String[]{s};
        } else if (Character.isDigit(s.charAt(0))) {      // project.skaro.expose.rdt.RdtCmtParser comment 2sxxxx
            clazz = project.skaro.expose.rdt.RdtCmtParser.class;
            param = new String[]{"comment", s};
        } else if (s.toLowerCase().startsWith("sub")) {      // project.skaro.expose.rdt.RdtCmtParser sub worldnews
            clazz = project.skaro.expose.rdt.RdtCmtParser.class;
            param = s.split(SPACE);
        } else if (s.toLowerCase().startsWith("gn")){       // GNReader
            clazz = project.skaro.expose.gn.GNReader.class;
       } else {
            super.process(s);
            return;
        }
        invoke(clazz.getName(), param);
    }
}
