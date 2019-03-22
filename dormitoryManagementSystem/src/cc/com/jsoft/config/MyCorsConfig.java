//package cc.com.jsoft.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//	解决CORS的同源策略的后端处理方式，需要加载cors需要的jar包
///**
// * @author spartajet
// * @description
// * @create 2018-05-15 下午5:00
// * @email spartajet.guo@gmail.com
// */
//@Configuration
//public class MyCorsConfig {
//	@Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://127.0.0.1:8080,http://localhost:8080")
//                        .allowedMethods("POST,GET, OPTIONS,DELETE,PUT")
//                        .allowedHeaders("Content-Type,ContentType,Access-Control-Allow-Headers,Access-Control-Allow-Origin, Authorization, X-Requested-With")
//                        .allowCredentials(true);
//            }
//        };
//}
//}
//
