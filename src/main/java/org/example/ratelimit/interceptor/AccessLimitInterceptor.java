package org.example.ratelimit.interceptor;

import org.example.ratelimit.annotation.AccessLimit;
import org.example.ratelimit.utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @program: demo
 *
 * @description:
 *
 * @author: Mr.Zhu
 *
 * @create: 2019-07-31 16:17
 **/
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

	@Autowired
    RedisTemplate redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			if (!method.isAnnotationPresent(AccessLimit.class)) {
				return true;
			}
			AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
			if (accessLimit == null) {
				return true;
			}
			int limit = accessLimit.limit();
			int sec = accessLimit.sec();
			String key = IPUtil.getIpAddress(request) + request.getRequestURI();
			Integer maxLimit = (Integer) redisTemplate.opsForValue().get(key);
			if (maxLimit == null) {
				//set时一定要加过期时间
				redisTemplate.opsForValue().set(key, 1, sec, TimeUnit.SECONDS);
			} else if (maxLimit < limit) {
				redisTemplate.opsForValue().set(key, maxLimit + 1, sec, TimeUnit.SECONDS);
			} else {
				output(response, "请求太频繁!");
				return false;
			}
		}
		return true;
	}

	public void output(HttpServletResponse response, String msg) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			outputStream.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			outputStream.flush();
			outputStream.close();
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
