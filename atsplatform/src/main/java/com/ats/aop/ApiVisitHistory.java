package com.ats.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Aspect
public class ApiVisitHistory {

    //@Autowired
    //private OperatorLogService operatorLogService;

    private Logger logger = LoggerFactory.getLogger(ApiVisitHistory.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    public static AtomicInteger count = new AtomicInteger(0);

    /**
     * 定义切面
     * - 此处代表com.ats.controller包下的所有接口都会被统计
     *//*
    @Pointcut("execution(* com.ats.controller..*.*(..)) && !execution(* com.ats.controller.LoginController.*(..)) " +
            "&& !execution(* com.ats.controller.RegisterController.*(..))")*/
    @Pointcut("execution(* com.ats.controller..*.*(..)) ")
    public void pointCut(){

    }

    /**
     * 在接口原有的方法执行前，将会首先执行此处的代码
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        logger.info("类名：{}", joinPoint.getSignature().getDeclaringType().getSimpleName());
        logger.info("方法名:{}", joinPoint.getSignature().getName() );
    }

    /**
     * 只有正常返回才会执行此方法
     * 如果程序执行失败，则不执行此方法
     */
    @AfterReturning(returning = "returnVal", pointcut = "pointCut()")
    public void doAfterReturning(JoinPoint joinPoint, Object returnVal) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.info("URI:[{}], 耗费时间:[{}] ms, 访问次数:{}", request.getRequestURI(), System.currentTimeMillis() - startTime.get(),count.incrementAndGet());
        /*User curUser = CommonUtil.getCurUser();
        if (curUser == null || curUser.getId() == null || curUser.getName() == null){
            return;
        }
        //记录操作日志
        OperatorLog operatorLog = new OperatorLog();
        //用户名
        operatorLog.setOperatorUsername(curUser.getName());
        //用户ip
        operatorLog.setOperatorIp(curUser.getLastLoginIp());
        //用户id
        operatorLog.setOperatorUid(curUser.getId());
        //路径
        operatorLog.setRequestUri(joinPoint.getSignature().getName());
        //操作类名
        operatorLog.setOperatorModule(joinPoint.getSignature().getDeclaringType().getSimpleName());

        boolean result = operatorLogService.insert(operatorLog);
        if (result){
            logger.info("操作日志更新成功");
        }else{
            logger.error("操作日志更新失败");
        }*/
    }

    /**
     * 当接口报错时执行此方法
     */
    @AfterThrowing(pointcut = "pointCut()")
    public void doAfterThrowing(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.info("接口访问失败，URI:[{}], 耗费时间:[{}] ms", request.getRequestURI(), System.currentTimeMillis() - startTime.get());
    }
}
