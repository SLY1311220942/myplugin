package com.sly.myplugin.preventrepeat.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sly.myplugin.base.result.BaseResultCode;
import com.sly.myplugin.base.result.Result;
import com.sly.myplugin.preventrepeat.annotation.PreventRepeat;
import com.sly.myplugin.preventrepeat.properties.PreventRepeatProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 防重复提交拦截器
 *
 * @author SLY
 * @date 2021/11/25
 */
@Component
public class PreventRepeatInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreventRepeatInterceptor.class);

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PreventRepeatProperties preventRepeatProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (!method.isAnnotationPresent(PreventRepeat.class)) {
            // 放行
            return true;
        }

        // 获取请求路径
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String requestPath = uri.replaceFirst(contextPath, "").replaceAll("/+", "/");
        // 获取超时时间
        PreventRepeat prevent = method.getAnnotation(PreventRepeat.class);
        long time = prevent.value();
        // 获取所有参数
        String paramStr = null;
        //
        String httpMethod = request.getMethod().toUpperCase();

        if (httpMethod.equals(METHOD_GET)) {
            JSONObject jsonObject = new JSONObject();
            Map<String, String[]> m = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : m.entrySet()) {
                String name = entry.getKey();
                String[] values = entry.getValue();
                String value = null;
                if (values != null && values.length > 0) {
                    value = values[0];
                }
                jsonObject.put(name, value);
            }
            paramStr = jsonObject.toJSONString();
        } else if (httpMethod.equals(METHOD_POST)) {
            Parameter[] parameters = method.getParameters();
            Object req = null;
            Annotation[][] annotationsList = method.getParameterAnnotations();
            OUT:
            for (int i = 0; i < annotationsList.length; i++) {
                Annotation[] annotations = annotationsList[i];
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() == RequestBody.class) {
                        req = parameters[i];
                        break OUT;
                    }
                }
            }
            paramStr = JSON.toJSONString(req);
        }
        //得到所有参数的md5串
        if (paramStr == null) {
            paramStr = "";
        }
        String md5 = DigestUtils.md5DigestAsHex(paramStr.getBytes());
        String token = "";
        if (StringUtils.hasText(preventRepeatProperties.getTokenKey())) {
            token = request.getHeader(preventRepeatProperties.getTokenKey());
        }
        String key = token + ":" + requestPath + ":" + md5;
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, "valid", time, TimeUnit.MILLISECONDS);
        if (result == null || !result) {
            //redis操作返回false，说明key还没有到期清除，此时提示用户操作过于频繁。
            response.setContentType("text/javascript;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.failed(BaseResultCode.PREVENT_REPEAT)));
            return false;
        }
        return true;
    }
}
