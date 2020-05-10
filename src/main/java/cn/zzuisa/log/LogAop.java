package cn.zzuisa.log;

import cn.zzuisa.config.TokenManager;
import cn.zzuisa.entity.Account;
import cn.zzuisa.log.annotation.BussinessLog;
import cn.zzuisa.log.factory.LogTaskFactory;
import cn.zzuisa.log.sub.LogManager;
import cn.zzuisa.service.AccountService;
import cn.zzuisa.utils.HostHolder;
import cn.zzuisa.utils.kit.Context;
import cn.zzuisa.utils.kit.IpUtil;
import org.apache.catalina.Host;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * 日志记录
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午8:48:30
 */
@Aspect
@Component
public class LogAop {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    AccountService accountService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "@annotation(cn.zzuisa.log.annotation.BussinessLog)")
    public void cutService() {
    }

    /**
     * ProceedingJoinPoint is only supported for around advice
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception {

        HttpServletRequest request = Context.getRequest();
        String ip = IpUtil.getClientIp(request);
        String url = request.getRequestURL().toString();

        Enumeration<String> hn = request.getHeaderNames();
        while (hn.hasMoreElements()) {
            String string = (String) hn.nextElement();
            System.out.println(string + ": " + request.getHeader(string));
        }

        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();

        //如果当前用户未登录，不做日志
        Account account = hostHolder.getUser();
        String token = request.getHeader("token");
        if (null == account) {
            if(token == null){
                account = new Account().setUsername("Someone").setId(-1);
            }else{
                account = accountService.getById(TokenManager.get(token));
            }
            if (account == null) {
                return;
            }
        }
        Long userId = Long.parseLong(account.getId().toString());
        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = annotation.value();
        String key = annotation.key();
//        Class dictClass = annotation.dict();

        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }

        String uri = request.getRequestURI();
        //如果涉及到修改,比对变化
        String msg = bussinessName;
        /*if (bussinessName.indexOf("修改") != -1 || bussinessName.indexOf("编辑") != -1) {
            Object obj1 = LogObjectHolder.me().get();
            Map<String, String> obj2 = HttpKit.getRequestParameters();
            msg = Contrast.contrastObj(dictClass, key, obj1, obj2);
        } else {
            Map<String, String> parameters = HttpKit.getRequestParameters();
            AbstractDictMap dictMap = (AbstractDictMap) dictClass.newInstance();
            msg = Contrast.parseMutiKey(dictMap,key,parameters);
        }*/

        LogManager.me().executeLog(LogTaskFactory.bussinessLog(userId, ip, url, uri, bussinessName, className, methodName, msg));
    }
}
