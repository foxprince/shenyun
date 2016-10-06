package cn.anthony.util;
/*
 * Created on 2004-12-27 13:40:14
 * Author:Anthony
 */

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anthony
 */
public class StringTools {
    public static String ZH_SPACE = "　";
    public static final String BIN_MSG = "<!EHAPPY_BIN_MSG!>";

    /**
     * 去掉换行符
     * 
     * @param str
     * @return
     */
    public static String trimCRLF(String s) {
	byte[] b = s.getBytes();
	byte[] c = new byte[b.length];
	int j = 0;
	for (byte element : b)
	    if (element == 10 || element == 13)
		continue;
	    else
		c[j++] = element;
	String s2 = new String(c);
	// s2 = s2.replaceAll(" ", "");
	return s2.trim();
    }

    /**
     * 把CRLF换成 <br/>
     * 
     * @param s
     * @return
     */
    public static String replaceCRLF(String s) {
	StringBuffer sb = new StringBuffer();
	Pattern p = Pattern.compile("\r\n");
	String[] r = p.split(s);
	for (int i = 0; i < r.length; i++) {
	    sb.append(r[i]);
	    if (i < r.length - 1)
		sb.append("<br/>");
	}
	return sb.toString();
    }

    public static byte[] gb2unicode(String str) {
	try {
	    return str.getBytes("UTF-16BE");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static String replaceSpace(String s) {
	StringBuffer sb = new StringBuffer();
	Pattern p = Pattern.compile(" |\t");
	String[] r = p.split(s);
	for (int i = 0; i < r.length; i++) {
	    sb.append(r[i]);
	    if (i < r.length - 1)
		sb.append("&nbsp;");
	}
	return sb.toString();
    }

    public static String checkNull(Object s) {
	if (s == null)
	    return null;
	if (s.toString().trim().length() == 0)
	    return null;
	if (s.toString().trim().equalsIgnoreCase("null"))
	    return null;
	if (s instanceof List && ((List) s).size() == 0)
	    return null;
	return s.toString();
    }

    public static String printString(Object s) {
	s = checkNull(s);
	if (s == null)
	    return "";
	else
	    return s.toString();
    }

    public static String getInvisibleString(String s) {
	if (s == null)
	    return null;
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < s.length(); i++)
	    sb.append("*");
	return sb.toString();
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     * 
     * @param arrB
     *            需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception
     *             本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
	int iLen = arrB.length;
	// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
	StringBuffer sb = new StringBuffer(iLen * 2);
	for (int i = 0; i < iLen; i++) {
	    int intTmp = arrB[i];
	    // 把负数转换为正数
	    while (intTmp < 0) {
		intTmp = intTmp + 256;
	    }
	    // 小于0F的数需要在前面补0
	    if (intTmp < 16) {
		sb.append("0");
	    }
	    sb.append(Integer.toString(intTmp, 16));
	}
	return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * 
     * @param strIn
     *            需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception
     *             本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:zhangji@aspire-tech.com">ZhangJi</a>
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
	byte[] arrB = strIn.getBytes();
	int iLen = arrB.length;

	// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
	byte[] arrOut = new byte[iLen / 2];
	for (int i = 0; i < iLen; i = i + 2) {
	    String strTmp = new String(arrB, i, 2);
	    arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
	}
	return arrOut;
    }

    public static List<String> splitString(String src, String token) {
	List<String> l = new LinkedList<String>();
	StringTokenizer st = new StringTokenizer(src, token);
	while (st.hasMoreTokens())
	    l.add(st.nextToken());
	return l;
    }

    public static boolean isValidPhone(String phone) {
	if (StringTools.checkNull(phone) == null)
	    return false;
	for (int i = 0; i < phone.length(); i++)
	    if ((phone.charAt(i) < '0') || (phone.charAt(i) > '9'))
		return false;
	return true;
    }

    public static String getChineseOnly(String src) {
	char ch[] = src.toCharArray();
	StringBuffer buffer = new StringBuffer();
	for (char element : ch) {
	    if ((element + "").matches("[\\u4e00-\\u9fa5]+"))
		buffer.append(element);
	}
	return buffer.toString();
    }

    public static String removeChinese(String src) {
	char ch[] = src.toCharArray();
	StringBuffer buffer = new StringBuffer();
	for (char element : ch) {
	    if (!(element + "").matches("[\\u4e00-\\u9fa5]+"))
		buffer.append(element);
	}
	return buffer.toString();
    }

    private static int countSize(int size, int base, int len) {
	StringBuffer sb = new StringBuffer();
	for (int i = 1; i <= base; i++) {
	    sb.append("(" + i + "/" + (base) + ")");
	}
	int newSize = sb.length() * 2 + size;
	int newBase = (int) Math.ceil((double) (newSize) / (double) len);
	// return newBase;
	if (newBase == base)
	    return base;
	else
	    return countSize(size, newBase, len);
    }

    public static boolean checkEmail(String email) {
	Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*",
		Pattern.CASE_INSENSITIVE);
	Matcher matcher = pattern.matcher(email);
	return matcher.matches();
    }

    /**
     * 提取两个字符串之间的内容
     * 
     * @param src
     * @param start
     * @param end
     * @return
     */
    public static String e(StringBuilder src, String start, String end) {
	int startIndex = src.indexOf(start);
	src.delete(0, startIndex);
	int endIndex = src.indexOf(end);
	String s = (src.substring(start.length(), endIndex).trim());
	src.delete(0, endIndex);
	return s.startsWith(":") || s.startsWith("：") ? s.substring(1) : s;
    }

    public static String eWithoutTrim(StringBuilder src, String start, String end) {
	int startIndex = src.indexOf(start);
	src.delete(0, startIndex);
	int endIndex = src.indexOf(end);
	String s = (src.substring(start.length(), endIndex));
	src.delete(0, endIndex);
	return s.startsWith(":") || s.startsWith("：") ? s.substring(1) : s;
    }

    /**
     * 提取正则表达式group命中的内容
     * 
     * @param src
     * @param regx
     * @return
     */
    public static String pe(String src, String regx) {
	Pattern p = Pattern.compile(regx);
	Matcher m = p.matcher(src);
	String s = null;
	if (m.find())
	    s = m.group(1).trim();
	s = s != null ? (s.startsWith(":") || s.startsWith("：") ? s.substring(1) : s) : null;
	return s;
    }

    /**
     * 提取正则表达式group命中的内容
     * 
     * @param src
     * @param regx
     * @return
     */
    public static String pe2(String src, String regx) {
	Pattern p = Pattern.compile(regx);
	Matcher m = p.matcher(src);
	String s = null;
	if (m.find())
	    s = m.group(2).trim();
	s = s != null ? (s.startsWith(":") || s.startsWith("：") ? s.substring(1) : s) : null;
	return s;
    }

    public static String formatMap(Map m) {
	StringBuilder sb = new StringBuilder();
	for (Iterator iterator = m.entrySet().iterator(); iterator.hasNext();) {
	    Entry entry = (Entry) iterator.next();
	    sb.append(entry.getKey() + ":" + entry.getValue() + "\n");
	}
	return sb.toString();
    }

    public static List<String> split(StringBuilder src, String c) {
	List<String> l = new ArrayList<String>();
	int index = src.indexOf(c);
	while (index >= 0) {
	    l.add(src.substring(0, index));
	    src.delete(0, index + c.length());
	    index = src.indexOf(c);
	}
	if (src.length() > 0)
	    l.add(src.toString());
	return l;
    }

    public static String[] splitToArray(String s, String c) {
	StringBuilder src = new StringBuilder(s);
	List<String> l = split(src, c);
	String[] a = new String[l.size()];
	l.toArray(a);
	return a;
    }
    
    public static String secToTime(int time,boolean pre) {  
        String timeStr = null;  
        int hour = 0;  
        int minute = 0;  
        int second = 0;  
        if (time <= 0)  
            return "00:00";  
        else {  
            minute = time / 60;  
            if (minute < 60) {  
                second = time % 60;  
                timeStr = (pre?"00:":"")+unitFormat(minute) + ":" + unitFormat(second);  
            } else {  
                hour = minute / 60;  
                if (hour > 99)  
                    return "99:59:59";  
                minute = minute % 60;  
                second = time - hour * 3600 - minute * 60;  
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);  
            }  
        }  
        return timeStr;  
    }  
  
    public static String unitFormat(int i) {  
        String retStr = null;  
        if (i >= 0 && i < 10)  
            retStr = "0" + Integer.toString(i);  
        else  
            retStr = "" + i;  
        return retStr;  
    }
    
    public static String second2m(double s,boolean pre) {
	return secToTime((int)s,pre);
    }
    public static void caluMarathon(int full) {
	double x = (2*3600+58*60+150)/42.195;
	double y = x-5;
	String sx = second2m(x,false);
	String sy = second2m(y,false);
	double z = x*5;
	System.out.println(x);
	StringBuilder sb = new StringBuilder();
	sb.append(sx+"\n");
	sb.append("05KM:\t"+second2m(z,true)+"\t"+second2m(z,true)+"\t"+sx+"\n");
	for(int i =2;i<8;i++) {
	    z += 5*y;
	    sb.append(i*5+"KM:\t"+second2m(z,true)+"\t"+second2m(5*y,true)+"\t"+sy+"\n");
	}
	z += 5*x;
	sb.append("40KM:\t"+second2m(z,true)+"\t"+second2m(5*x,true)+"\t"+sx+"\n");
	z += 2.195*x;
	sb.append("END :\t"+second2m(z,true)+"\t"+second2m(2.195*x,true)+"\t"+sx+"\n");
	System.out.println(sb.toString());
    }
    
    public static void main(String[] args) {
	caluMarathon(1);
	StringBuilder sb = new StringBuilder();
	sb.append("正常\n");
	sb.append("\n");
	sb.append("ddd");
	//System.out.println(URLDecoder.decode("http%3A%2F%2Fwww.xuxian.com%2Findex.php%3Fcontroller%3Dgame_bargain%26action%3Dindex&response_type=code&scope=snsapi_base&state=123&connect_redirect=1#wechat_redirect"));
	// String[] ss = StringUtils.split(sb.toString(), c);
	// System.out.println(ss.length + ":" + Arrays.asList(ss));
	Map<String, List<String>> map = new HashMap<String, List<String>>() {
	    {
		put("a", new ArrayList<String>());
	    }
	};
	// System.out.println(checkNull(map.get("a")));
	// StringBuilder s = new StringBuilder("传染病史:dd\n活史dd个人史fgfgdfg个人生活史");
	// System.out.println(pe(s.toString(), "传染病史((\\s|\\S)*?)(个人生活史|个人史)"));
	// Pattern p = Pattern.compile("<text>(\\s*)</text>");
	// Matcher m = p.matcher("<text>科室</text>");
	// System.out.println(m.matches());
	// while (m.find()) {
	// System.out.println(m.group());
	// // System.out.println(m.group(2));
	// // System.out.println("end(): " + m.end());
	// }
    }

    public static <T> String formatCollection(Collection<T> l) {
	StringBuilder sb = new StringBuilder();
	for (T t : l) {
	    sb.append(t.toString() + "\n");
	}
	return sb.toString();
    }

    public static String readDataFromConsole(String prompt) {
	Console console = System.console();
	if (console == null) {
	    throw new IllegalStateException("Console is not available!");
	}
	return console.readLine(prompt);
    }

    public static String readDataFromIn(String prompt) {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String str = null;
	try {
	    System.out.print(prompt);
	    str = br.readLine();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return str;
    }
}