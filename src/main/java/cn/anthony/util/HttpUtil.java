package cn.anthony.util;

import java.util.Map;

public class HttpUtil {
    public static String toGetString(String url, Map<String, String> paras) {
	StringBuilder sb = new StringBuilder(url);
	sb.append("&");
	for (Map.Entry<String, String> entry : paras.entrySet()) {
	    sb.append(entry.getKey() + "=" + entry.getValue() + "&");
	}
	return sb.substring(0, sb.length() - 1);
    }
}
