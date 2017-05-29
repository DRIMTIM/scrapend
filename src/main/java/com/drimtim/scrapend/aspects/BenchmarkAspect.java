package com.drimtim.scrapend.aspects;

import com.drimtim.scrapend.annotations.Benchmark;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Aspect
@Component
public class BenchmarkAspect {

    private static final Logger LOGGER = LogManager.getLogger(BenchmarkAspect.class);

    //TODO: Fix when @Benchmark annotation is used by async method because it doesn't work.
    @Around("@annotation(com.drimtim.scrapend.annotations.Benchmark)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Benchmark benchmark = method.getDeclaredAnnotation(Benchmark.class);

        if (benchmark != null) {
            LOGGER.info("################## BENCHMARK ##################");
            LocalDateTime startTime = LocalDateTime.now();
            long totalMemoryBytes = Runtime.getRuntime().totalMemory();
            long freeMemoryBytes = Runtime.getRuntime().freeMemory();
            String name = signature.getDeclaringType().getName() + "." + signature.getMethod().getName();
            LOGGER.info("## JVM Total Memory Before: " + Long.divideUnsigned(totalMemoryBytes, 1024) + " kb, " + Long.divideUnsigned(Long.divideUnsigned(totalMemoryBytes, 1024), 1024) + " Mb.");
            LOGGER.info("## JVM Free Memory Before: " + Long.divideUnsigned(freeMemoryBytes, 1024) + " kb, " + Long.divideUnsigned(Long.divideUnsigned(freeMemoryBytes, 1024), 1024) + " Mb.");
            LOGGER.info("## Executing: " + name);
            LOGGER.info("################## BENCHMARK ##################");
            Object result = joinPoint.proceed();
            LocalDateTime endTime = LocalDateTime.now();
            totalMemoryBytes = Runtime.getRuntime().totalMemory();
            freeMemoryBytes = Runtime.getRuntime().freeMemory();
            LOGGER.info("################## BENCHMARK ##################");
            LOGGER.info("## Executed: " + name);
            LOGGER.info("## JVM Total Memory After: " + Long.divideUnsigned(totalMemoryBytes, 1024) + " kb, " + Long.divideUnsigned(Long.divideUnsigned(totalMemoryBytes, 1024), 1024) + " Mb.");
            LOGGER.info("## JVM Free Memory After: " + Long.divideUnsigned(freeMemoryBytes, 1024) + " kb, " + Long.divideUnsigned(Long.divideUnsigned(freeMemoryBytes, 1024), 1024) + " Mb.");
            LOGGER.info("## Results:");
            long milliseconds = ChronoUnit.MILLIS.between(startTime, endTime);
            long seconds = ChronoUnit.SECONDS.between(startTime, endTime);
            long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
            LOGGER.info("## " + milliseconds + (milliseconds  > 1 ? " Milliseconds." : " Millisecond."));
            LOGGER.info("## " + seconds + (seconds  > 1 ? " Seconds." : " Second."));
            LOGGER.info("## " + minutes + (minutes  > 1 ? " Minutes." : " Minute."));
            LOGGER.info("################## BENCHMARK ##################");
            return result;
        } else {
            return joinPoint.proceed();
        }

    }
}
