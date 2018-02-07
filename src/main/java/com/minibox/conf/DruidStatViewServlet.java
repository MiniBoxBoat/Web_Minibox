package com.minibox.conf;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;

public class DruidStatViewServlet implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        FilterRegistration.Dynamic filter = servletContext.addFilter("druidFilter",
                WebStatFilter.class);
        filter.setInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filter.setInitParameter("sessionStatMaxCount","1000");
        filter.setInitParameter("sessionStatEnable", "false");
        filter.setInitParameter("profileEnable","true");

        Dynamic druidServlet = servletContext.addServlet("duridServlet", StatViewServlet.class);
        druidServlet.addMapping("/druid/*");
        druidServlet.setInitParameter("resetEnable","true");
        druidServlet.setInitParameter("loginUsername","may");
        druidServlet.setInitParameter("loginPassword","123456");
    }
}
