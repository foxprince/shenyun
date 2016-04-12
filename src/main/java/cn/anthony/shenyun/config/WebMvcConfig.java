package cn.anthony.shenyun.config;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 如果想全面控制Spring MVC，你可以添加自己的@Configuration，并使用@EnableWebMvc对其注解。如果想保留SpringBoot
 * MVC的特性，并只是添加其他的MVC配置(拦截器，formatters，视图控制器等)，
 * 你可以添加自己的WebMvcConfigurerAdapter类型的@Bean（不使用@EnableWebMvc注解）。
 */

// @Configuration
// @EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	converters.add(createGsonHttpMessageConverter());
	super.configureMessageConverters(converters);

    }

    private GsonHttpMessageConverter createGsonHttpMessageConverter() {
	Gson gson = new GsonBuilder()// .excludeFieldsWithoutExposeAnnotation()//
				     // 不导出实体中没有用@Expose注解的属性
		.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
	gsonConverter.setGson(gson);
	return gsonConverter;
    }
}