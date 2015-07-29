package com.example.OlesyukNV.myapplication.backend;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Фильтр кодировки для TaskServlet
 *
 * @author Q-OLN
 */
public class CharsetFilter implements Filter {
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        //encoding = "UTF-8";
        encoding = "Cp1251";
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException,
            ServletException {
        response.setCharacterEncoding(encoding);
        next.doFilter(request, response);
    }

    public void destroy() {
    }
}