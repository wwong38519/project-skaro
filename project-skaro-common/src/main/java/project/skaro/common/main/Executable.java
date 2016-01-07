package project.skaro.common.main;

import project.skaro.common.util.CommonUtils;
import project.skaro.common.util.ExecUtils;


/**
 * @version $Id$
 */
public interface Executable {

    public void execute(String[] args);
    
    public static class Display {
        
        public static Class<?> clazz = CommonUtils.class;
        
        public static void log(Object... s) {
            ExecUtils.invoke(clazz.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), Object[].class, s);
        }

        public static void console(Object... s) {
            ExecUtils.invoke(clazz.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), Object[].class, s);
        }
        
        public static void console(String s) {
           ExecUtils.invoke(clazz.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), String.class, s);
        }
        
        public static void separate() {
           ExecUtils.invoke(clazz.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), null, null);
        }
    }
}
