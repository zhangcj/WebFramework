package com.beecho.web.render;

import com.beecho.web.Beecho;
import com.beecho.web.BeechoContext;
import com.beecho.web.Const;
import com.beecho.web.filter.BeechoFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/7/14.
 */
public class JspRender implements Render{
    private static final Logger LOGGER = Logger.getLogger(JspRender.class.getName());

    public void render(String view, Writer writer) {
        String viewPath = view;//this.getViewPath(view);
        LOGGER.info("viewPath:" + viewPath);

        HttpServletRequest servletRequest = BeechoContext.instance().getRequest().getRaw();
        HttpServletResponse servletResponse = BeechoContext.instance().getResponse().getRaw();
        try {
            servletRequest.getRequestDispatcher(viewPath).forward(servletRequest, servletResponse);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getViewPath(String view) {
        Beecho beecho = Beecho.instance();
        String viewPrfix = beecho.getConf(Const.VIEW_PREFIX_FIELD);
        String viewSuffix = beecho.getConf(Const.VIEW_SUFFIX_FIELD);

        if (null == viewSuffix || viewSuffix.equals("")) {
            viewSuffix = Const.VIEW_SUFFIX;
        }
        if (null == viewPrfix || viewPrfix.equals("")) {
            viewPrfix = Const.VIEW_PREFIX;
        }
        String viewPath = viewPrfix + "/" + view;
        if (!view.endsWith(viewSuffix)) {
            viewPath += viewSuffix;
        }
        return viewPath.replaceAll("[/]+", "/");
    }
}
