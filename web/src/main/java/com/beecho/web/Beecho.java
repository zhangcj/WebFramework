package com.beecho.web;

import com.beecho.web.config.ConfigLoader;
import com.beecho.web.render.JspRender;
import com.beecho.web.render.Render;
import com.beecho.web.route.Routers;
import com.beecho.web.wrapper.Request;
import com.beecho.web.wrapper.Response;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/14.
 */
public final class Beecho {

    /**
     * 存放所有路由
     */
    private Routers routers;

    /**
     * 配置加载器
     */
    private ConfigLoader configLoader;

    /**
     * 框架是否已经初始化
     */
    private boolean init = false;

    /**
     * 渲染器
     */
    private Render render;

    private Beecho() {
        routers = new Routers();
        configLoader = new ConfigLoader();
        render = new JspRender();
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    private static class MarioHolder {
        private static Beecho instance = new Beecho();
    }

    public static Beecho instance() {
        return MarioHolder.instance;
    }

    public Beecho loadConf(String conf) {
        configLoader.load(conf);
        return this;
    }

    public Beecho setConf(String name, String value) {
        configLoader.setConf(name, value);
        return this;
    }

    public String getConf(String name) {
        return configLoader.getConf(name);
    }

    public Beecho addRoutes(Routers routers) {
        this.routers.addRoute(routers.getRoutes());
        return this;
    }

    public Routers getRouters() {
        return routers;
    }

    /**
     * 添加路由
     *
     * @param path       映射的PATH
     * @param methodName 方法名称
     * @param controller 控制器对象
     * @return 返回Mario
     */
    public Beecho addRoute(String path, String methodName, Object controller) {
        try {
            Method method = controller.getClass().getMethod(methodName, Request.class, Response.class);
            this.routers.addRoute(path, method, controller);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Render getRender() {
        return render;
    }

    public void setRender(Render render) {
        this.render = render;
    }

}
