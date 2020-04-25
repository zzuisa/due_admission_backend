package cn.zzuisa.log.factory;

import cn.zzuisa.entity.Log;
import cn.zzuisa.log.sub.LogManager;
import cn.zzuisa.log.sub.SpringContextHolder;
import cn.zzuisa.mapper.AccountMapper;
import cn.zzuisa.mapper.LogMapper;
import cn.zzuisa.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午9:18:27
 */
public class LogTaskFactory {
    @Autowired
    static AccountService accountService;

    private static Logger logger = LoggerFactory.getLogger(LogManager.class);
    /*private static LoginLogMapper loginLogMapper = Db.getMapper(LoginLogMapper.class);
    private static OperationLogMapper operationLogMapper = Db.getMapper(OperationLogMapper.class);*/
    private static LogMapper logsMapper = SpringContextHolder.getBean(LogMapper.class);

    public static TimerTask loginLog(final Integer userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    /*LoginLog loginLog = LogFactory.createLoginLog(LogType.LOGIN, userId, null, ip);
                    loginLogMapper.insert(loginLog);*/
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginLog(final String username, final String msg, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                /*LoginLog loginLog = LogFactory.createLoginLog(
                        LogType.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip);*/
                try {
                    /*loginLogMapper.insert(loginLog);*/
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final Integer userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
//                LoginLog loginLog = LogFactory.createLoginLog(LogType.EXIT, userId, null,ip);
                try {
//                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask bussinessLog(final Long userId, String ip,
                                         String url, String uri, final String bussinessName, final String clazzName, final String methodName, final String msg) {

        return new TimerTask() {
            @Override
            public void run() {
                System.out.println("创建日志对象");
                /*OperationLog operationLog = LogFactory.createOperationLog(
                        LogType.BUSSINESS, userId, bussinessName, clazzName, methodName, msg, LogSucceed.SUCCESS);*/
                try {
                    Log logs = new Log();
                    logs.setContent(msg.split("#")[0]);
                    logs.setUserId(userId.toString());
                    logs.setIp(ip);
                    logs.setUrl(url);
                    logs.setType(msg.split("#")[1]);
                    logs.setCreateTime(new Date());
                    logsMapper.insert(logs);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final Integer userId, final Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                /*String msg = ToolUtil.getExceptionMsg(exception);
                OperationLog operationLog = LogFactory.createOperationLog(
                        LogType.EXCEPTION, userId, "", null, null, msg, LogSucceed.FAIL);
                try {
                    operationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }*/
            }
        };
    }
}
