package cn.stb.stbcrmserver.config;

import cn.stb.stbcrmserver.base.AuthorityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 解决跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorityInterceptor()).addPathPatterns("/**").excludePathPatterns("/LoginController/**");
    }

    @Bean
    public AuthorityInterceptor getAuthorityInterceptor() {
        return new AuthorityInterceptor();
    }
}

