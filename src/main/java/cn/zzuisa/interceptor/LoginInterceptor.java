package cn.zzuisa.interceptor;

import cn.zzuisa.config.TokenManager;
import cn.zzuisa.config.UnloginException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        System.out.println(TokenManager.all());
        boolean o = TokenManager.isLogin(request.getHeader("token").replaceAll("\"",""));
        if(!o) {
            throw new UnloginException("未登錄");
        }
        return true;
    }

}
