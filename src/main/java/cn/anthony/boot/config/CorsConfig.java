package cn.anthony.boot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@Configuration
public class CorsConfig {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean corsFilter() {
		//if(constant.SUPPORT_CORS){
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			CorsConfiguration config = new CorsConfiguration();
			//config.setAllowCredentials(true);
			config.addAllowedOrigin("*");
			config.addAllowedHeader("*");
			config.addAllowedMethod("*");
			source.registerCorsConfiguration("/**", config);
			FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
			bean.setOrder(0);
			return bean;
		//}
		//return null;
	}
}
