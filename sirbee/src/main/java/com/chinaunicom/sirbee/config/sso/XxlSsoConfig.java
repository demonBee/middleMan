//package com.chinaunicom.sirbee.config;
//
//import com.xxl.sso.core.store.SsoLoginStore;
//import com.xxl.sso.core.util.JedisUtil;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author xuxueli 2018-04-03 20:41:07
// */
//@Configuration
//public class XxlSsoConfig implements InitializingBean, DisposableBean {
//
////    @Value("${qpsafety.sso.redis.address}")
////    private String redisAddress;
//
//    @Value("${qpsafety.sso.redis.expire.minite}")
//    private int redisExpireMinite;
//
//    @Value("${spring.redis.sentinel.nodes}")
//    private String sentinelNodes;
//
//    @Value("${spring.redis.sentinel.master}")
//    private String master;
//
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        SsoLoginStore.setRedisExpireMinite(redisExpireMinite);
////        JedisUtil.init(redisAddress);
////        JedisUtil.init(sentinelNodes);
//
//        JedisUtil.initSentinel(sentinelNodes,master);
//
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        JedisUtil.close();
//    }
//
//}
