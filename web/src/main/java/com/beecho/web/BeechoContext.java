package com.beecho.web;

import com.beecho.web.wrapper.Request;
import com.beecho.web.wrapper.Response;

import javax.servlet.ServletContext;

/**
 * Created by Administrator on 2017/7/14.
 */
public class BeechoContext {

    private static final ThreadLocal<BeechoContext> CONTEXT = new ThreadLocal<BeechoContext>();

    private ServletContext context;
    private Request request;
    private Response response;

    private BeechoContext() {
    }

    public static BeechoContext instance(){
        return CONTEXT.get();
    }

    public static void initContext(ServletContext context, Request request, Response response) {
        BeechoContext marioContext = new BeechoContext();
        marioContext.context = context;
        marioContext.request = request;
        marioContext.response = response;
        CONTEXT.set(marioContext);
    }

    public static void remove(){
        CONTEXT.remove();
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
