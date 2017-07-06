package cn.anthony.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import cn.anthony.util.http.HTTPUtil;

public class BJGuahao {
	String loginUrl = "http://www.bjguahao.gov.cn/quicklogin.htm";
	String testUrl = "http://www.bjguahao.gov.cn/u/index.htm";
	String loginErrorUrl = "http://www.bjguahao.gov.cn/tologin.htm";
	String dutyUrl = "http://www.bjguahao.gov.cn/dpt/partduty.htm";
	String sendOrderUrl = "http://www.bjguahao.gov.cn/v/sendorder.htm";
	String confirmUrl = "http://www.bjguahao.gov.cn/order/confirm.htm";
	String hospitalId = "142";
	String departmentId = "200039602";
	String dutyDate = "2016-06-06";
	// 创建CookieStore实例
	static CookieStore cookieStore = null;
	static HttpClientContext context = null;

	public static void main(String[] args) {
		BJGuahao b = new BJGuahao();
		try {
			System.out.println("====");
			// System.out.println(StringTools.readDataFromIn("iiii:"));
			b.gh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gh() throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(loginUrl);
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("mobileNo", "13311032007");
		parameterMap.put("password", "pwd4bjgh");
		parameterMap.put("isAjax", "true");
		parameterMap.put("yzm", "");
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(HTTPUtil.getParam(parameterMap), "UTF-8");
		httpPost.setEntity(postEntity);
		System.out.println("request line:" + httpPost.getRequestLine());
		try {
			// 执行post请求
			HttpResponse httpResponse = client.execute(httpPost);
			int status = httpResponse.getStatusLine().getStatusCode();
			System.out.println("login response:" + status);
			if (status >= 200 && status < 300)
				System.out.println("login response:" + status);
			// 执行get请求
			HttpGet httpGet = new HttpGet(testUrl);
			System.out.println("request line:" + httpGet.getRequestLine());
			client.execute(httpGet);
			// cookie store
			setCookieStore(httpResponse);
			// context
			setContext();
			// 请求获得挂号页面地址
			String ghUrl = null, docId = null, sourceId = null;
			boolean avalible = false;
			String json = null;
			while (!avalible) {
				// 重复刷新，只到放号
				httpPost.releaseConnection();
				httpPost = new HttpPost(dutyUrl);
				parameterMap.clear();
				parameterMap.put("hospitalId", hospitalId);
				parameterMap.put("departmentId", departmentId);
				parameterMap.put("dutyCode", "1");
				parameterMap.put("dutyDate", dutyDate);
				parameterMap.put("isAjax", "true");
				postEntity = new UrlEncodedFormEntity(HTTPUtil.getParam(parameterMap), "UTF-8");
				httpPost.setEntity(postEntity);
				httpResponse = client.execute(httpPost);
				json = EntityUtils.toString(httpResponse.getEntity());
				System.out.println("====:" + json);
				try {
					Thread.sleep(100);
					Duty duty = new Gson().fromJson(json, Duty.class);
					for (DutyData data : duty.data) {
						if (data.remainAvailableNumber > 0 && data.doctorTitleName.indexOf("14") > 0 && data.skill.indexOf("运动伤") > 0) {
							docId = data.doctorId;
							sourceId = data.dutySourceId;
							ghUrl = "http://www.bjguahao.gov.cn/order/confirm/" + hospitalId + "-" + departmentId + "-" + data.doctorId
									+ "-" + data.dutySourceId + ".htm";
							// 放号，停止循环
							avalible = true;
						}
					}
				} catch (Exception e) {
					System.out.println("没放号");
				}
			}
			httpGet.releaseConnection();
			System.out.println(ghUrl);
			httpGet = new HttpGet(ghUrl);
			int statusCode = client.execute(httpGet).getStatusLine().getStatusCode();
			System.out.println(statusCode);
			httpPost.releaseConnection();
			httpPost = new HttpPost(sendOrderUrl);
			statusCode = client.execute(httpPost).getStatusLine().getStatusCode();
			System.out.println(statusCode);
			String smsCode = StringTools.readDataFromIn("短信码：");
			parameterMap.clear();
			parameterMap.put("dutySourceId", sourceId);
			parameterMap.put("hospitalId", hospitalId);
			parameterMap.put("departmentId", departmentId);
			parameterMap.put("doctorId", docId);
			parameterMap.put("patientId", "202208986");
			parameterMap.put("hospitalCardId", "");
			parameterMap.put("medicareCardId", "");
			parameterMap.put("reimbursementType", "-1");
			parameterMap.put("smsVerifyCode", smsCode);
			parameterMap.put("isFirstTime", "2");
			parameterMap.put("hasPowerHospitalCard", "2");
			parameterMap.put("cidType", "1");
			parameterMap.put("childrenBirthday", "");
			parameterMap.put("childrenGender", "2");
			parameterMap.put("isAjax", "true");
			httpPost.releaseConnection();
			httpPost = new HttpPost(confirmUrl);
			postEntity = new UrlEncodedFormEntity(HTTPUtil.getParam(parameterMap), "UTF-8");
			httpPost.setEntity(postEntity);
			httpResponse = client.execute(httpPost);
			json = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("====:" + json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setCookieStore(HttpResponse httpResponse) {
		System.out.println("----setCookieStore");
		cookieStore = new BasicCookieStore();
		// JSESSIONID
		String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
		String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
		System.out.println("JSESSIONID:" + JSESSIONID);
		// 新建一个Cookie
		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
		cookie.setVersion(0);
		cookie.setDomain("www.bjguahao.gov.cn");
		cookie.setPath("/");
		// cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
		// cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
		// cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
		// cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
		cookieStore.addCookie(cookie);
	}

	public static void setContext() {
		System.out.println("----setContext");
		context = HttpClientContext.create();
		Registry<CookieSpecProvider> registry = RegistryBuilder.<CookieSpecProvider>create()
				.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).build();
		context.setCookieSpecRegistry(registry);
		context.setCookieStore(cookieStore);
	}

	class Duty {
		List<DutyData> data;
	}

	class DutyData {
		String dutySourceId, doctorId, doctorTitleName, skill;
		Integer remainAvailableNumber;
	}

	class Confirm {
	}
}
