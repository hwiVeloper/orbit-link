package dev.hwiveloper.orbitlink.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* dev.hwiveloper.orbitlink.controller..*.*(..))")
    private void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        SimpleDateFormat dayTime = new SimpleDateFormat("HH:mm:ss.SSS");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        long startTime = System.currentTimeMillis();
        String remoteIp = request.getRemoteAddr(); // 요청IP

        String sessionId = ObjectUtils.isEmpty(request.getSession().getId()) ? "" : request.getSession().getId();
        if(sessionId.length() > 20) {
            MDC.put("sessionId", "["+sessionId.substring(0,20)+"]");
        }

        String reqTime = dayTime.format(new Date(startTime));

        log.info("▷START [#"+startTime+"] - "+pjp.getSignature().getDeclaringTypeName()+"/"+pjp.getSignature().getName() + ", remoteIp:["+remoteIp+"]" + ", time:["+reqTime+"]");

        // 1. 요청 파라미터 (query string 등)
        StringBuffer paramLog = new StringBuffer();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            paramLog.append(key).append("=").append(request.getParameter(key)).append(" ");
        }
        if (!ObjectUtils.isEmpty(paramLog.toString())) {
            log.info("▷REQUEST PARAMS [{}]", paramLog.toString().trim());
        }

        // 2. 요청 바디 (JSON 등)
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            String body = new String(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            if (!ObjectUtils.isEmpty(body)) {
                log.info("▷REQUEST BODY [{}]", body);
            }
        } else {
            log.warn("Request is not wrapped with ContentCachingRequestWrapper, cannot log request body");
        }

        Object[] args = pjp.getArgs();

        Object result = pjp.proceed(args);

        // 응답 결과 로깅
        if (result != null) {
            try {
                if (result instanceof byte[] || result instanceof InputStreamResource || result instanceof MultipartFile) {
                    log.info("▷RESPONSE BODY [binary content omitted]");
                } else {
                    ObjectMapper om = new ObjectMapper();
                    om.registerModule(new JavaTimeModule());
                    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    String responseJson = om.writeValueAsString(result);
                    log.info("▷RESPONSE BODY [{}]", responseJson);
                }
            } catch (Exception e) {
                log.warn("Failed to log response body", e);
            }
        }

        long endTime = System.currentTimeMillis();
        String resTime = dayTime.format(new Date(endTime));

        log.info("▷END [#"+startTime+"] - "+pjp.getSignature().getDeclaringTypeName()+"/"+pjp.getSignature().getName() + ", time:["+resTime+"]" + ", currTime:["+(endTime-startTime)+"]");

        MDC.clear();

        return result;
    }
}
