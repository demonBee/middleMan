//package com.chinaunicom.sirbee.config;
//
//import com.xxl.sso.core.conf.Conf;
//import com.xxl.sso.core.filter.XxlSsoTokenFilter;
//import com.xxl.sso.core.util.JedisUtil;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author xuxueli 2018-11-15
// */
//@Configuration
//public class XxlSsoSecondConfig implements DisposableBean {
//
//
//    @Value("${qpsafety.sso.server}")
//    private String xxlSsoServer;
//
//    @Value("${qpsafety.sso.logout.path}")
//    private String xxlSsoLogoutPath;
//
//    @Value("${qpsafety.sso.excluded.paths}")
//    private String xxlSsoExcludedPaths;
//
////    @Value("${qpsafety.sso.redis.address}")
////    private String xxlSsoRedisAddress;
//
//    @Value("${spring.redis.sentinel.nodes}")
//    private String sentinelNodes;
//    @Value("${spring.redis.sentinel.master}")
//    private String master;
//
//
//    @Bean
//    public FilterRegistrationBean xxlSsoFilterRegistration() {
//
//        // xxl-sso, redis init
////        JedisUtil.init(xxlSsoRedisAddress);
////        JedisUtil.init(sentinelNodes);
//        JedisUtil.initSentinel(sentinelNodes,master);
//
//        // xxl-sso, filter init
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//
//        registration.setName("XxlSsoWebFilter");
//        registration.setOrder(1);
//        registration.addUrlPatterns("/*");
//        registration.setFilter(new XxlSsoTokenFilter());
//        registration.addInitParameter(Conf.SSO_SERVER, xxlSsoServer);
//        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, xxlSsoLogoutPath);
//        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, xxlSsoExcludedPaths);
//
//        return registration;
//    }
//
//    @Override
//    public void destroy() throws Exception {
//
//        // xxl-sso, redis close
//        JedisUtil.close();
//    }
//
//}
