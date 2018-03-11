package com.minibox.conf;

import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.Enumeration;

//这个类会成为spring框架的入口   具体看spring实战139页
public class WebApplicationXml extends AbstractAnnotationConfigDispatcherServletInitializer {

/*    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.default", "dev");

        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebApplication.class);

        servletContext.addListener(new ContextLoaderListener(applicationContext));

        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("spring-mvc-dispatcher",
                        new DispatcherServlet());
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
        dispatcher.setInitParameter("contextConfigLocation", "")

    }*/

    //用于配置WebApplicationContext中的Bean，这里面的Bean可以用来共享，但是这里没有意义因为只有一个Dispatcher
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootContextConfig.class, CachingConfig.class, RedisConfig.class};
    }

    //用于配置Dispatcher的context的Bean
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfig.class};
    }

    //用于映射url
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.do"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CharacterEncodingFilter("UTF-8")};
    }

    //这个是在注册Dispatcher的时候会调用的一个方法，所以可以拿来配置Dispatcher
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("spring.profiles.default","pro");
        registration.setMultipartConfig(new MultipartConfigElement("/tmp/uploads", 2097152,4194304, 0));
    }
}
