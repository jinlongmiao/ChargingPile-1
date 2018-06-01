package com.swust.back.config.system;

import com.swust.back.model.User;
import com.swust.back.model.UserLog;
import com.swust.back.service.UserLogService;
import com.swust.back.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LogInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Resource
    private UserLogService userLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        try {
            if (handler instanceof HandlerMethod) {

                HandlerMethod handlerMethod = (HandlerMethod) handler;
//                String beanName = handlerMethod.getBean().getClass().toString();
//                String methodName = handlerMethod.getMethod().getName();
                String uri = request.getRequestURI().substring(1);
                String remoteAddr = getIpAddr(request);
                Session session = SecurityUtils.getSubject().getSession();
                String userId = ((User)session.getAttribute(Constants.SESSION_USER_INFO)).getAccount();
                String module = uri.split("/")[0] ;
                String action = uri.split("/")[1];
//                String userId = (String) request.getSession().getAttribute(ProjectConstant.SESSION_USER_INFO);
                String method = request.getMethod();
                UserLog userLog = new UserLog();
                userLog.setAction(action);
                userLog.setLoginIp(remoteAddr);
                userLog.setModule(module);
                userLog.setUserId(userId);
                logger.info("请求方式为="+method);
//                Map params = null;
//                if("POST".equals(method)){
//                    logger.info("this is POST!!!!!!!!!");
//                }else{
//                    params = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//                }
                logger.info("uri=" + uri + "; module=" + module + "; action=" + action + "; remoteAddr=" + remoteAddr + "; user=" + userId);
//                // 取不到值
//                MethodParameter[] mps = ((HandlerMethod) handler).getMethodParameters();
//                for(MethodParameter mp : mps){
//                    logger.info(mp.getParameterName() + " --  "+ mp.getParameterType());
//                }
//                // 取不到值
//                Map<String, String[]> params2 = request.getParameterMap();


                //userLogService 没有注入成功时，重新获取；
                if (userLogService == null) {
                    logger.info("userLogService is null !!!");
                    BeanFactory factory =
                            WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());

                    userLogService = (UserLogService) factory.getBean("userLogService");

                }

                userLogService.addLog(userLog);
            }
        } catch (Exception e) {
            logger.error("用户操作日志记录异常", e);
        }
//        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }


    //获取客户端IP
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
