package com.example.post.api.filter;

import com.example.post.api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtTokenFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtUtil.resolveToken((HttpServletRequest) request);
        if(token != null && jwtUtil.validateToken(token)){
            Authentication authentication = jwtUtil.getAuthentication(token);
            if(authentication != null){
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
