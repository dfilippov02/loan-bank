package edu.neoflex.filters;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@Log4j2
@WebFilter(filterName = "LoggerFilter", urlPatterns = "/*")
public class LoggerFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(req, resp);

        byte[] requestBody = req.getContentAsByteArray();
        byte[] responseBody = resp.getContentAsByteArray();

        log.info("request body = {}", new String(requestBody, StandardCharsets.UTF_8));
        log.info("response body = {}", new String(responseBody, StandardCharsets.UTF_8));

        resp.copyBodyToResponse();
    }
}
