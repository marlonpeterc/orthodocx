package nz.co.orthodocx.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    private static Logger log = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    @Around("@annotation(nz.co.orthodocx.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            return null;
        } finally {
            long end = System.currentTimeMillis();
            long executionTime = end - start;
            log.info(String.format("[Execution Time] - %s executed in %d ms.", joinPoint.getSignature(), executionTime));
            if(executionTime > 1000) {
                log.warn(String.format("[Execution Time] - %s execution took longer than 1 sec!", joinPoint.getSignature()));
            }
        }
    }

}
