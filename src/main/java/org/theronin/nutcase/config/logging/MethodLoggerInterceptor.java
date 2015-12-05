package org.theronin.nutcase.config.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.aspectj.lang.annotation.Pointcut;

/**
 * This Interceptor is responsible for logging genaral information about actual
 * classname methodname and params.
 *
 * @author Michael Koppen
 */
@Aspect
public class MethodLoggerInterceptor {

    public MethodLoggerInterceptor() {
    }

    @Pointcut("@annotation(org.theronin.nutcase.config.logging.Logged)")
    public void onlyLoggedAnnotatedTypes() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    /**
     * Logging of genaral information about actual classname methodname and
     * params.
     *
     * @param pjp ProceedingJoinPoint for execution of intercepted method
     * @return result of the executed method
     * @throws Throwable Thrown exception of the executed method
     */
    @Around("onlyLoggedAnnotatedTypes()")
    public Object intercept(final ProceedingJoinPoint pjp) throws Throwable {
        final Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        Logger LOG = LoggerFactory.getLogger(method.getDeclaringClass().getName());
        StringBuilder log = new StringBuilder("---------------------------------------------------------\n");

        log.append(" + Class: ").append(method.getDeclaringClass().getSimpleName()).append("\n");
        log.append(" -    Method: ").append(method.getName()).append("\n");

        if (method.getParameterTypes() != null) {
            final Logged loggedAnnotation = method.getAnnotation(Logged.class);
            final Annotation[][] annos = method.getParameterAnnotations();
            Class<?>[] params = method.getParameterTypes();
            for (int i = 0; i < annos.length; i++) {

                for (int j = 0; j < annos[i].length; j++) {
                    final Annotation annotation = annos[i][j];
                    log.append(" -       Annotation for Param ").append(i + 1).append(": @").append(annotation.annotationType().getSimpleName()).append("\n");
                }

                Object value = "<suppressed>";
                if (loggedAnnotation.printParameterValues()) {
                    value = pjp.getArgs()[i];
                }
                if (params[i] != null) {
                    final String className = params[i].getSimpleName();
                    log.append(" -       Param ").append(i + 1).append(": (").append(className).append(") ").append(value).append("\n");
                } else {
                    log.append(" -       Param ").append(i + 1).append(": () ").append(value).append("\n");
                }
            }
        }

        Object retVal;
        try {
            retVal = pjp.proceed(pjp.getArgs());
            log.append(" -       ReturnValue ").append(": ").append(retVal);
            LOG.info(log.toString());
        } catch (Exception e) {
            log.append(" -       Threw Exception ").append(": ").append(e.getClass().getSimpleName());
            LOG.info(log.toString());
            throw e;
        }

        return retVal;
    }

}
