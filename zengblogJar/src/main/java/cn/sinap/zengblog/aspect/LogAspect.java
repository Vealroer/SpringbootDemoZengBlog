package cn.sinap.zengblog.aspect;

import com.sun.tools.sjavac.server.RequestHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志aop处理通知类
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义切面
     */
    @Pointcut("execution(* cn.sinap.zengblog.controller.*.*(..))")
    public void log(){

    }

    /**
     * 前置通知
     */
    @Before(value = "log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." +
                joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        ResultLog resultLog = new ResultLog(url, ip, classMethod, args);
        logger.info("Request : {} ",resultLog);
    }

    /**
     * 最终通知
     */
    @After(value = "log()")
    public void doAfter(){

    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("result : {}",result);
    }


    private class ResultLog{

        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ResultLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ResultLog{" +
                    "url='" + url + '\'' +
                    ", id='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }


}
