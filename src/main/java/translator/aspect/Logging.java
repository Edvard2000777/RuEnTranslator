package translator.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Logging {

    @Pointcut("@annotation(Loggable)")
    public void invokeLogging() {
    }

    @Around("invokeLogging()")
    private Object log(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object returnValue = point.proceed();
        String res = "Executed: " + point.getSignature().getName();
        res += "With args: " + Arrays.toString(point.getArgs());
        System.out.println(res);
        System.out.println("Output was:"+returnValue);
        System.out.println("Time complexity: " + (System.currentTimeMillis() - start));
        return returnValue;
    }
}
