package com.cn.client.aop;

import com.cn.client.annotation.OperationLog;
import com.cn.client.bean.LoginUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class OperationLogAspect {

    @Pointcut("@annotation(com.cn.client.annotation.OperationLog)")
    public void pointcut(){

    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        LoginUser loginUser = (LoginUser) request.getAttribute("user");
        long starttime = System.currentTimeMillis();
        //方法执行
        result = point.proceed();
        long time = System.currentTimeMillis() - starttime;

        this.saveLog(point,time,loginUser != null?loginUser.getId():0);
        return result;
    }

    public void saveLog(ProceedingJoinPoint point,long time,Integer userId){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //获取注解上的注释描述
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        System.out.println(className+"."+methodName+"()");
        if(operationLog != null){
            System.out.println(userId + "-记录日志"+operationLog.value()+"----time----"+time);
        }
        Object[] args = point.getArgs();
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramsNames = parameterNameDiscoverer.getParameterNames(method);
        if(args != null && paramsNames != null){
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i<args.length;i++) {
                stringBuilder.append("  ").append(paramsNames[i]).append(": ").append(args[i]);
            }
            System.out.println(stringBuilder);
        }
    }
}
