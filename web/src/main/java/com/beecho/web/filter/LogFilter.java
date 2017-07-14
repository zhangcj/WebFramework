package com.beecho.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/14.
 */
public class LogFilter implements Filter {
    private FilterConfig config;

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("begin do the filter");
        this.config = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletContext context = this.config.getServletContext();
        System.out.println("before the filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("filter 已经拦截用户请求地址:" + ((HttpServletRequest) servletRequest).getServletPath());
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("after the filter");
    }

    public void destroy() {
        this.config = null;
    }
}
