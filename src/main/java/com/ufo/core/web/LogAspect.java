package com.ufo.core.web;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ufo.core.context.RuntimeContext;
import com.ufo.core.context.RuntimeContextBuilder;
import com.ufo.core.context.RuntimeContextThreadLocal;
import com.ufo.core.service.assembler.AssemblerMananger;

/** 
* 类名称：LogAspect 
* 类描述： web log aspect
* 
* 
* 创建人：Duzj
* 创建时间：2012-4-9 上午9:03:15 
* @version 
* 
*/
@Component
@Aspect
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private long begin;

    /** 
    * 创建运行时,数据
    */
    private void build() {
        RequestAttributes attrs = RequestContextHolder.currentRequestAttributes();
        if (attrs instanceof ServletRequestAttributes && null == RuntimeContextThreadLocal.get()) {
            ServletRequestAttributes attr = (ServletRequestAttributes) attrs;
            HttpServletRequest req = attr.getRequest();
            RuntimeContextBuilder.build(req);
        }
        showRequestInfo();
    }

    /** 
    * 清理缓存 
    */
    private void clean() {
        AssemblerMananger.cleanCache();
        RuntimeContextThreadLocal.clear();
    }

    /** 
     * 显示浏览请求相关信息数据
     */
    private void showRequestInfo() {
        RuntimeContext runtimeContext = RuntimeContextThreadLocal.get();
        if (logger.isDebugEnabled() && null != runtimeContext) {
            logger.debug(runtimeContext.toString());
        }
    }

    @Pointcut("execution(public * com.ufo..*.*Controller.*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void beforeExecute(JoinPoint joinPoint) {
        if (logger.isDebugEnabled()) {
            logger.debug("Controller begin");
            begin = System.currentTimeMillis();
        }
        build();
    }

    @AfterReturning(pointcut = "controllerPointcut()", returning = "retVal")
    public void afterExecuet(JoinPoint thisJoinPoint, Object retVal) {
        if (logger.isDebugEnabled()) {
            logger.debug("Controller end");
            String clsName = thisJoinPoint.getTarget().getClass().getName();
            String name = thisJoinPoint.getSignature().getName();
            logger.debug("执行" + clsName + "." + name + "方法用时:" + (System.currentTimeMillis() - begin));
        }
        clean();
    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint thisJointPoint, Exception ex) {
        if (logger.isDebugEnabled()) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Pointcut("execution(public * com.ufo..*.*WebService.*(..))")
    public void webServicePointcut() {
    }

    @Before("webServicePointcut()")
    public void beforeWsExecute(JoinPoint joinPoint) {
        if (logger.isDebugEnabled()) {
            logger.debug("Web Service Begin");
            begin = System.currentTimeMillis();
        }
        build();
    }

    @AfterReturning(pointcut = "webServicePointcut()", returning = "retVal")
    public void afterWsExecuet(JoinPoint thisJoinPoint, Object retVal) {
        if (logger.isDebugEnabled()) {
            logger.debug("Web Service end");
            logger.debug("执行 webService 用时:" + (System.currentTimeMillis() - begin));
        }
        clean();
    }

    @AfterThrowing(pointcut = "webServicePointcut()", throwing = "ex")
    public void afterWsThrowing(JoinPoint thisJointPoint, Exception ex) {
        if (logger.isDebugEnabled()) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
