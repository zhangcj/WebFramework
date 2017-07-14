package com.beecho.web;

import com.beecho.web.example.controller.HomeController;

/**
 * Created by Administrator on 2017/7/14.
 */
public class App implements Bootstrap {
    public void init(Beecho beecho) {
        HomeController home =  new HomeController();
        beecho.addRoute("/","index",home);
    }
}
