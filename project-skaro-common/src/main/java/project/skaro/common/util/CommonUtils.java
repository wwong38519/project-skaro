package project.skaro.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Strings;

public class CommonUtils implements Constants {
    
    private static Logger logger = Logger.getLogger(CommonUtils.class);

    public static void echo(Object... s) {
        for (int i = 0; i < s.length; i++)
            System.out.print(s[i]);
        System.out.println();
    }
    
    public static void log(Object... s) {
        logger.debug(StringUtils.join(s, SPACE));
    }
    
    public static void separate() {
        console(Strings.repeat(SEPARATOR, WIDTH/SEPARATOR.length()));
    }

    public static void console(Object... s) {
        console(StringUtils.join(s, SPACE));
    }

    public static void console(String s) {
        split(WIDTH, clean(s));
    }

    private static void split(int width, String s) {
        while (s.length() > width) {
            int x = s.substring(0, width).lastIndexOf(SPACE);
            if (x <= 0) x = s.indexOf(SPACE);
            if (x <= 0) {
                break;
            } else {
                log(s.substring(0, x).trim());
                s = s.substring(x).trim();
            }
        }
        if (s.trim().length() > 0) log(s.trim());
    }

    public static String clean(String s) {
        return StringEscapeUtils.unescapeHtml(replaceHex(removeSpace(removeLine(removeTags(extractLink(s))))));
    }
    
    private static String removeSpace(String s) {
        return removePattern(s, REMOVE_SPACE, SPACE);
    }
    private static String removeLine(String s) {
        return removePattern(s, REMOVE_LINE, SPACE);
    }
    private static String removeTags(String s) {
        return removePattern(s, REMOVE_TAGS, SPACE);
    }
    private static String removePattern(String s, Pattern pattern, String replacer) {
        return (s==null||s.length()==0)?s:pattern.matcher(s).replaceAll(replacer);
    }
    
    private static String extractLink(String s) {
        if (StringUtils.isBlank(s)) return BLANK;
        Matcher matcher = A_TAG.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String text = matcher.group(2);
            Matcher matcherHref = A_HREF_TAG.matcher(matcher.group(1));
            String link = BLANK;
            while (matcherHref.find()) link = matcherHref.group(1);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(COMMENT + text + SPACE + link + COMMENT));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    public static String replaceHex(String s) {
        int start = StringUtils.indexOf(s, HEX_PREFIX);
        int end = StringUtils.indexOf(s, HEX_SUFFIX, start+1);
        if (start == StringUtils.INDEX_NOT_FOUND || end == StringUtils.INDEX_NOT_FOUND || end <= start) return s; 
        int pos = 0;
        StringBuffer sb = new StringBuffer();
        while (start != StringUtils.INDEX_NOT_FOUND && end != StringUtils.INDEX_NOT_FOUND) {
            sb.append(s.substring(pos, start)).append(fromHex(s.substring(start, end+1)));
            pos = end+1;
            start = StringUtils.indexOf(s, HEX_PREFIX, end);
            end = StringUtils.indexOf(s, ";", start+1);
        }
        return sb.append(s.substring(pos)).toString();
    }
    
    private static char fromHex(String s) {
        return (char) Integer.parseInt(s.replaceFirst(HEX_PREFIX, BLANK).replaceAll(HEX_SUFFIX, BLANK), 16);
    }
    
    private static int WIDTH = 140;
    private static final Pattern REMOVE_SPACE = Pattern.compile("( |\t){2,}");
    private static final Pattern REMOVE_LINE = Pattern.compile("\n");
    private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
    private static final Pattern A_TAG = Pattern.compile("(?i)<a([^>]+)>(.+?)</a>");
    private static final Pattern A_HREF_TAG = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
    private static final String HEX_PREFIX = "&#x";
    private static final String HEX_SUFFIX = ";";
}
