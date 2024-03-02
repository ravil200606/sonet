package ars.otus.sonet.configuration.filter;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestIdFilter implements Filter {
    private static final String REQUEST_ID_HEADER = "requestId";
    public static final ThreadLocal<String> requestId = new ThreadLocal<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestIdValue = httpServletRequest.getHeader(REQUEST_ID_HEADER);
        requestId.set(requestIdValue);
        filterChain.doFilter(servletRequest, servletResponse);
        requestId.remove();

    }
}
