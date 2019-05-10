package com.alon.ssh.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangyl on 2019/4/26
 */
public class PatternUtil {
    public static final String ENTER = "[\\r, \\n, \\r\\n]";

    public static boolean isEnter(String str) {
        Pattern p = Pattern.compile(ENTER);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
