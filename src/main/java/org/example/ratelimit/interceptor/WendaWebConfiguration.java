package org.example.ratelimit.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @program: demo
 *
 * @description:
 *
 * @author: Mr.Zhu
 *
 * @create: 2019-07-31 17:23
 **/
@Component
public class WendaWebConfiguration extends WebMvcConfigurationSupport {
	@Autowired
	AccessLimitInterceptor addInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(addInterceptor);
		super.addInterceptors(registry);
	}
}
