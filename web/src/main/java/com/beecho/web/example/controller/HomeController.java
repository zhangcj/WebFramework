package com.beecho.web.example.controller;

import com.beecho.web.BeechoContext;
import com.beecho.web.wrapper.Request;
import com.beecho.web.wrapper.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/7/14.
 */
public class HomeController {

    public void index(Request request, Response response) {
        request.attr("framewrok", "BeechoFramework");
        response.render("index");
    }
}