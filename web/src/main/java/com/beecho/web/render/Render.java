package com.beecho.web.render;

import java.io.Writer;

/**
 * Created by Administrator on 2017/7/14.
 */
public interface Render {

    /**
     * 渲染到视图
     * @param view		视图名称
     * @param writer	写入对象
     */
    public void render(String view, Writer writer);

}
