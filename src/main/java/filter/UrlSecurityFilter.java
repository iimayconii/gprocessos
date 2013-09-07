/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author maycon
 */
public class UrlSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String pageRequested = req.getRequestURI().toString();
        StringBuffer requestURL = req.getRequestURL();
        System.out.println("Testando ooo");
        if (!pageRequested.contains("primefaces") && !pageRequested.contains("jquery.jsf") && !pageRequested.contains("css.jsf") && !pageRequested.contains("js.jsf")) {
            if (!pageRequested.endsWith("/index.jsf") && !pageRequested.endsWith("/error.jsf")) {
                Pattern pattern = Pattern.compile(".*\\.xhtml");
                Matcher matcher = pattern.matcher(pageRequested);
                boolean match = matcher.find();
                if (!match) {
                    pattern = Pattern.compile(".*\\.jsf");
                    matcher = pattern.matcher(pageRequested);
                    match = matcher.find();
                }
                if (match) {
                    resp.sendRedirect("/error.jsf");
                }
            }
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }
}
