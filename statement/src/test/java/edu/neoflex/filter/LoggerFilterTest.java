package edu.neoflex.filter;

import edu.neoflex.filters.LoggerFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggerFilterTest {

    @Mock
    FilterChain filterChain;

    Logger mock;

    @InjectMocks
    LoggerFilter loggerFilter;


    @Test
    void doFilterInternal() throws ServletException, IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        loggerFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(any(ContentCachingRequestWrapper.class), any(ContentCachingResponseWrapper.class));
    }
}