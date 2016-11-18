package cn.anthony;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
public class ExceptionResolver implements HandlerExceptionResolver {

	private static Logger log = Logger.getLogger(ExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Class<?> beanType = handlerMethod.getBeanType();
		String method = handlerMethod.getMethod().getName();
		log.error(beanType+"执行"+method+"方法的时候发生错误：" + ex.getMessage(),ex);
		MAYLResult maylResult = null;
		if(ex.getMessage()!=null && !"".equals(ex.getMessage())){
			ex.printStackTrace();
			maylResult = MAYLResult.getResponsePage(false, ex.getMessage());
		}else{
			maylResult = MAYLResult.build(false, "服务器未知错误");
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String result;
		try {
			result = JSON.toJSONString(maylResult);
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
