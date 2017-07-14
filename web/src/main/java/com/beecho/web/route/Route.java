package com.beecho.web.route;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/14.
 */
public class Route {

    public Route(){}

    /**
     * 路由path
     */
    private String path;
    /**
     * 执行路由的方法
     */
    private Method action;
    /**
     * 路由所在的控制器
     */
    private Object controller;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
}
