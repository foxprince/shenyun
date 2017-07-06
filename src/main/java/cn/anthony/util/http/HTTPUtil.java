/* 
 * @author anthony
 * 2008-4-2
 * HTTPUtil.java
 *
 */
package cn.anthony.util.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HTTPUtil {
	public void test(String[] args) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("mobileNo", "13311032007");
		m.put("password", "pwd4bjgh");
		m.put("isAjax", "true");
		m.put("yzm", "");
		java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
		java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");
		// System.setProperty("log4j.logger.org.apache.httpclient.wire.header",
		// "WARN");
		// System.setProperty("log4j.logger.httpclient.wire.content", "WARN");
		// log4j.logger.httpclient.wire.header=WARN
		// log4j.logger.httpclient.wire.content=WARN
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		String JSESSIONID = "DDA69959CD14DEE8970A5C4A842999F2";
		// 新建一个Cookie
		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
		cookie.setVersion(0);
		cookie.setDomain("http://www.bjguahao.gov.cn");
		cookie.setPath("/");
		BasicClientCookie cookie2 = new BasicClientCookie("SESSION_COOKIE", "3cab1829cea362dbceb67f7e");
		cookie2.setVersion(0);
		cookie2.setDomain("http://www.bjguahao.gov.cn");
		cookie2.setPath("/");// cookie.setAttribute(ClientCookie.VERSION_ATTR,
		// "0");
		// cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
		// cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
		// cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
		cookieStore.addCookie(cookie);
		cookieStore.addCookie(cookie2);
		try {
			HttpGet httpget = new HttpGet("http://www.bjguahao.gov.cn/u/index.htm");
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				int status = response1.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response1.getEntity();
					String s = (entity != null ? EntityUtils.toString(entity) : null);
					System.out.println(s);
					if (s.indexOf("我的预约") >= 0)
						System.out.println("login on");
					else
						System.out.println("not login");
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			} finally {
				response1.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String get(String url) {
		String s = null;
		CloseableHttpClient httpclient = HttpClients.custom().build();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				s = (entity != null ? EntityUtils.toString(entity) : null);
				return s;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return s;
	}

	String encoding = System.getProperty("sun.jnu.encoding");

	public static List<NameValuePair> toNameValuePairs(Map<String, String> params) {
		List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
		for (Map.Entry<String, String> entry : params.entrySet()) {
			NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
			valuePairs.add(nameValuePair);
		}
		return valuePairs;
	}

	public static String postJson(String url, String jsonStr) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = null;
		try {
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
			HttpResponse httpresponse = httpclient.execute(httppost);
			HttpEntity entity = httpresponse.getEntity();
			responseBody = EntityUtils.toString(entity);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.close();
		}
		return responseBody;
	}

	public static void main(String[] args) {
	}

	public static void printResponse(HttpResponse httpResponse) throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		System.out.println("status:" + httpResponse.getStatusLine());
		System.out.println("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			System.out.println("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println("response length:" + responseString.length());
			System.out.println("response content:" + responseString.replace("\r\n", ""));
		}
	}

	public static List<NameValuePair> getParam(Map parameterMap) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Iterator it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry parmEntry = (Entry) it.next();
			param.add(new BasicNameValuePair((String) parmEntry.getKey(), (String) parmEntry.getValue()));
		}
		return param;
	}

	public static String toGetString(String url, Map<String, String> paras) {
		StringBuilder sb = new StringBuilder(url);
		sb.append("&");
		for (Map.Entry<String, String> entry : paras.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public static String toGetStringExcludePage(String url, Map<String, String> paras) {
		StringBuilder sb = new StringBuilder(url);
		sb.append("&");
		for (Map.Entry<String, String> entry : paras.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase("page"))
				sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		return sb.substring(0, sb.length() - 1);
	}
}
