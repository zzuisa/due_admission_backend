package cn.zzuisa.utils.kit;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class Context {

	/**
	 * 获取HttpServletRequest
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
    	return servletRequestAttributes.getRequest();
	}
}
