package cn.jackiegu.spring.aop.mixture;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aspect切面类
 *
 * @author JackieGu
 * @date 2021/5/11
 */
@Component
@Aspect
public class SpringAopMixtureAspect {

    @SuppressWarnings("all")
    @Pointcut("execution(* cn.jackiegu.spring.aop..*ServiceImpl.*(..))")
    private void pointcut() {
    }

    /**
     * 环绕通知, 可定义代理方法执行前后逻辑, 并控制代理方法的执行
     *
     * @param pjp 增强连接点, 公开了proceed方法
     * @return 代理方法的返回值
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around start");
        Object result = pjp.proceed();
        System.out.println("around end");
        return result;
    }

    /**
     * 前置通知, 在代理方法执行前执行
     *
     * @param jp 连接点
     */
    @Before("pointcut()")
    public void before(JoinPoint jp) {
        System.out.println("before");
    }

    /**
     * 后置通知, 在代理方法退出后执行(包括正常退出和异常退出), 类使用finally
     *
     * @param jp 连接点
     */
    @After("pointcut()")
    public void after(JoinPoint jp) {
        System.out.println("after");
    }

    /**
     * 后置通知, 在代理方法正常退出后执行
     *
     * @param jp 连接点
     */
    @AfterReturning("pointcut()")
    public void afterReturning(JoinPoint jp) {
        System.out.println("after returning");
    }

    /**
     * 异常通知, 在代理方法异常退出后执行
     *
     * @param jp 连接点
     * @param throwable 异常
     */
    @AfterThrowing(value = "pointcut()", throwing="throwable")
    public void afterThrowing(JoinPoint jp, Throwable throwable) {
        System.out.println("after throwing");
        System.err.println(throwable.toString());
    }
}
