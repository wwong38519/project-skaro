package project.skaro.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public interface Constants {

    public static final String BLANK = "";
    public static final String SPACE = " ";
    public static final String INDENT = ".";
    public static final String SEPARATOR = ".";
    public static final String UNDERSCORE = "_";
    public static final String COMMENT = "#";
    public static final String AMP = "&";
    public static final String EQ = "=";
    public static final String UTF_8 = "UTF-8";
    
    // TimeInterval
    public static final String DELIM = " / ";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final NumberFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
    
    public static final String KEY_RDB_TOKEN = "readability.token";
    public static final String KEY_INPUT_PATH = "input.path";
}
