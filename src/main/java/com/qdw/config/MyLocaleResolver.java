package com.qdw.config;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {

    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //获取请求中的语言参数
        String parameter = request.getParameter("l");
        Locale locale = Locale.getDefault();
        System.out.println("parameter->"+parameter);
        //如果携带了国际化参数
        if (!"".equals(parameter) && parameter != null){
            String[] s = parameter.split("_");
            locale = new Locale(s[0], s[1]);
        }
        System.out.println("locale->"+locale);

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
