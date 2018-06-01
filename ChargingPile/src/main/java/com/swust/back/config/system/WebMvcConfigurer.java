package com.swust.back.config.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC 配置
 */
@Configuration
@Deprecated
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {


    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

    /**
     * 解决dispatcherServlet拦截静态资源的问题
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Bean
    public HandlerInterceptor getInterceptor(){
        return new LogInterceptor();
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(getInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**");
        super.addInterceptors(registry);
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("forward:/index.html");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        super.addViewControllers(registry);
//    }



//
//    private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);
//    @Value("${spring.profiles.active}")
//    private String env;//当前激活的配置文件
//
//    //使用阿里 FastJson 作为JSON MessageConverter
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,//保留空的字段
//                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
//                SerializerFeature.WriteNullNumberAsZero);//Number null -> 0
//        converter.setFastJsonConfig(config);
//        converter.setDefaultCharset(Charset.forName("UTF-8"));
//        converters.add(converter);
//    }
//
//
//    //解决跨域问题
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
//                .allowCredentials(true);
//    }

    //统一异常处理
//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//        exceptionResolvers.add(new HandlerExceptionResolver() {
//            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
//                Result result = new Result();
//                if (e instanceof ServiceException) {//业务失败的异常，如“账号或密码错误”
//                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
//                    logger.info(e.getMessage());
//                } else if (e instanceof NoHandlerFoundException) {
//                    result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
//                } else if (e instanceof ServletException) {
//                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
//                } else {
//                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
//                    String message;
//                    if (handler instanceof HandlerMethod) {
//                        HandlerMethod handlerMethod = (HandlerMethod) handler;
//                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
//                                request.getRequestURI(),
//                                handlerMethod.getBean().getClass().getName(),
//                                handlerMethod.getMethod().getName(),
//                                e.getMessage());
//                    } else {
//                        message = e.getMessage();
//                    }
//                    logger.error(message, e);
//                }
//                responseResult(response, result);
//                return new ModelAndView();
//            }
//
//        });
//    }





//    private void responseResult(HttpServletResponse response, Result result) {
//        System.out.println("mvc里的response" );
////        CustomUtils.show(response);
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-type", "application/json;charset=UTF-8");
//
//        response.setStatus(200);
//        try {
//            response.getWriter().write(JSON.toJSONString(result));
//        } catch (IOException ex) {
//            logger.error(ex.getMessage());
//        }
//    }

    /**
     * 一个简单的签名认证，规则：
     * 1. 将请求参数按ascii码排序
     * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
     * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
     */
//    private boolean validateSign(HttpServletRequest request) {
//        String requestSign = request.getParameter("sign");//获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
//        if (StringUtils.isEmpty(requestSign)) {
//            return false;
//        }
//        List<String> keys = new ArrayList<String>(request.getParameterMap().keySet());
//        keys.remove("sign");//排除sign参数
//        Collections.sort(keys);//排序
//
//        StringBuilder sb = new StringBuilder();
//        for (String key : keys) {
//            sb.append(key).append("=").append(request.getParameter(key)).append("&");//拼接字符串
//        }
//        String linkString = sb.toString();
//        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);//去除最后一个'&'
//
//        String secret = "Potato";//密钥，自己修改
//        String sign = DigestUtils.md5Hex(linkString + secret);//混合密钥md5
//
//        return StringUtils.equals(sign, requestSign);//比较
//    }

//    private String getIpAddress(HttpServletRequest request) {
//       return CustomUtils.getIpAddr(request);
//    }
}
