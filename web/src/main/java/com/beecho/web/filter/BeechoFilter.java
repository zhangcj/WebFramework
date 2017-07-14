package com.beecho.web.filter;

import com.beecho.web.Beecho;
import com.beecho.web.BeechoContext;
import com.beecho.web.Bootstrap;
import com.beecho.web.Const;
import com.beecho.web.route.Route;
import com.beecho.web.route.RouteMatcher;
import com.beecho.web.route.Routers;
import com.beecho.web.util.PathUtil;
import com.beecho.web.util.ReflectUtil;
import com.beecho.web.wrapper.Request;
import com.beecho.web.wrapper.Response;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/7/14.
 */
public class BeechoFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(BeechoFilter.class.getName());
    private RouteMatcher routeMatcher = new RouteMatcher(new ArrayList<Route>());
    private ServletContext servletContext;

    public void init(FilterConfig filterConfig) throws ServletException {
        Beecho beecho = Beecho.instance();
        if (!beecho.isInit()) {
            String className = filterConfig.getInitParameter("bootstrap");
            Bootstrap bootstrap = this.getBootstrap(className);
            bootstrap.init(beecho);

            Routers routers = beecho.getRouters();
            if(null!=routers){
                routeMatcher.setRoutes(routers.getRoutes());
            }
            servletContext = filterConfig.getServletContext();
            beecho.setInit(true);
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(Const.DEFAULT_CHAR_SET);
        response.setCharacterEncoding(Const.DEFAULT_CHAR_SET);

        // 请求 uri
        String uri = PathUtil.getRelativePath(request);
        LOGGER.info("request URI:" + uri);

        Route route = routeMatcher.findRoute(uri);
        if (null != route) {
            handle(request, response, route);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void destroy() {

    }

    private Bootstrap getBootstrap(String className) {
        if (null != className) {
            try {
                Class<?> clazz = Class.forName(className);
                Bootstrap bootstrap = (Bootstrap) clazz.newInstance();
                return bootstrap;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("init bootstrap class error!");
    }

    private void handle(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,Route route) {
        Request request = new Request(httpServletRequest);
        Response response = new Response(httpServletResponse);
        BeechoContext.initContext(servletContext, request, response);

        Object controller = route.getController();
        Method action = route.getAction();
        executeMethod(controller, action, request, response);
    }

    private Object executeMethod(Object object, Method method, Request request, Response response) {
        int len = method.getParameterTypes().length;
        method.setAccessible(true);
        if (len > 0) {
            Object[] args = getArgs(request, response, method.getParameterTypes());
            return ReflectUtil.invokeMehod(object, method, args);
        } else {
            return ReflectUtil.invokeMehod(object, method);
        }
    }

    private Object[] getArgs(Request request,Response response,Class<?>[] params) {
        int len = params.length;
        Object[] args = new Object[len];

        for (int i = 0; i < len; i++) {
            Class<?> paramTypeClazz = params[i];
            if (paramTypeClazz.getName().equals(Request.class.getName())) {
                args[i] = request;
            }
            if (paramTypeClazz.getName().equals(Request.class.getName())) {
                args[i] = response;
            }
        }

        return args;
    }
}
