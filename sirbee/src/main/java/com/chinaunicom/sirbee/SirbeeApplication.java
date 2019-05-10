package com.chinaunicom.sirbee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScan("com.chinaunicom.sirbee.dao.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class SirbeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SirbeeApplication.class, args);
    }



//implements WebMvcConfigurer
//    /**
//     * 解决跨域问题
//     *
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //设置允许跨域的路径
//        registry.addMapping("/**")
//                //设置允许跨域请求的域名
//                .allowedOrigins("*")
//                //是否允许证书 不再默认开启
//                .allowCredentials(true)
//                //设置允许的方法
//                .allowedMethods("*")
//                //跨域允许时间
//                .maxAge(3600);
//    }


}



