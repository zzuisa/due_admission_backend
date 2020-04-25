package cn.zzuisa.aspect;

import cn.zzuisa.entity.Log;
import cn.zzuisa.mapper.LogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ao
 * @date 2020-04-24 17:18
 */
@Component
@Aspect
public class ServiceLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Pointcut("execution(* cn.zzuisa.service.*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before(JoinPoint joinpoint) {
        // 用户[1,2,3,4] 在[xxx] 访问了 xxx service
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            System.out.println("___________________________");
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String target = joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName();
        logger.info(String.format("用户[%s],在[%s]，访问了[%s]", ip, now, target));
    }
}
