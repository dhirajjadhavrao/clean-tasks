package com.demo.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
    private static final String  DISPACHER_SERVLET_NAME ="dispatcher";
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context =
                new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);

        //Create Dispatcher Servlet
        DispatcherServlet dispatcherServlet =
                new DispatcherServlet(context);

        //Register and configure dispatcher servlet
        ServletRegistration.Dynamic registration =
                servletContext.addServlet(DISPACHER_SERVLET_NAME, dispatcherServlet);

        registration.setLoadOnStartup(1);
        registration.addMapping("/");

    }
}
