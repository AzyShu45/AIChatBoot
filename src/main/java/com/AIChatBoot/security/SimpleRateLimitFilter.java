package com.AIChatBoot.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class SimpleRateLimitFilter implements Filter {

    // Store timestamps per IP
    private final ConcurrentHashMap<String, Long> ipTimestamps = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String ip = request.getRemoteAddr();
        long now = System.currentTimeMillis();

        Long lastTime = ipTimestamps.get(ip);
        // 1 second between requests
        long REQUEST_INTERVAL_MS = 1000;
        if (lastTime != null && (now - lastTime < REQUEST_INTERVAL_MS)) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(429); // Too Many Requests
            res.getWriter().write("âŒ Too many requests. Please wait a moment.");
            return;
        }

        ipTimestamps.put(ip, now);
        chain.doFilter(request, response);
    }
}