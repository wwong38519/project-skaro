package project.skaro.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ExecUtils {

    public static void invoke(String className, String methodName, Object arg) {
        invoke(className, methodName, arg.getClass());
    }
    
    public static void invoke(String className, String methodName, Class argClass, Object arg) {
        try {
            Class<?> clazz = Class.forName(className);
            Object object = clazz.newInstance();
            if (argClass == null) {
                Method method = clazz.getMethod(methodName);
                if (Modifier.isStatic(method.getModifiers())) {
                    method.invoke(null);
                }
                else {
                    method.invoke(object); // null for class method (instantiation for instance method)
                }
            }
            else {
                Method method = clazz.getMethod(methodName, argClass);
                if (Modifier.isStatic(method.getModifiers())) {
                    method.invoke(null, arg);
                }
                else {
                    method.invoke(object, arg); // null for class method (instantiation for instance method)
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }
    
}
