package com.fusi24.auth.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fusi24.auth.service.SecretService;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	Logger log = Logger.getLogger(WebSecurityConfig.class);

    @Autowired
    CsrfTokenRepository jwtCsrfTokenRepository;

    @Autowired
    SecretService secretService;

    // ordered so we can use binary search below
//    private String[] ignoreCsrfAntMatchers = { "/auth", "/create-token", "/check-token", "/dynamic-builder-compress", "/dynamic-builder-general", "/dynamic-builder-specific", "/set-secrets" };
    private String[] ignoreCsrfAntMatchers = { "/auth", "/check-token", "/create-token", "/token-error"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new JwtCsrfValidatorFilter(), CsrfFilter.class)
            .csrf()
            .csrfTokenRepository(jwtCsrfTokenRepository)
            .ignoringAntMatchers(ignoreCsrfAntMatchers)
            .and()
            .authorizeRequests()
            .antMatchers("/**")
            .permitAll();
    }

    private class JwtCsrfValidatorFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            // NOTE: A real implementation should have a nonce cache so the token cannot be reused

//            CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
//
//            if (
//            // only care if it's a POST
//            //"POST".equals(request.getMethod()) &&
//            // ignore if the request path is in our list
//                Arrays.binarySearch(ignoreCsrfAntMatchers, request.getServletPath()) < 0 &&
//                // make sure we have a token
//                token != null) {
//                // CsrfFilter already made sure the token matched. Here, we'll make sure it's not expired
//                try {
//                    Jwts.parser()
//                        .setSigningKeyResolver(secretService.getSigningKeyResolver())
//                        .parseClaimsJws(token.getToken());
//                } catch (JwtException e) {
//                    // most likely an ExpiredJwtException, but this will handle any
//                    request.setAttribute("exception", e);
//                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                    RequestDispatcher dispatcher = request.getRequestDispatcher("expired-jwt");
//                    dispatcher.forward(request, response);
//                }
//            }
        	
        	String token = request.getHeader("x-api-key");
        	
        	if (// ignore if the request path is in our list
    			Arrays.binarySearch(ignoreCsrfAntMatchers, request.getRequestURI()) < 0
    			) {
        		
        		log.info("Request uri=" + request.getRequestURI() + " & token=");
        		
        		if(token != null && !token.isEmpty()) {
        			// Token provided
        			try {
                      Jwts.parser()
                          .setSigningKeyResolver(secretService.getSigningKeyResolver())
                          .parseClaimsJws(token);
	                  } catch (JwtException e) {
	                      // most likely an ExpiredJwtException, but this will handle any
	                      request.setAttribute("exception", e);
	                      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                      RequestDispatcher dispatcher = request.getRequestDispatcher("token-error");
	                      dispatcher.forward(request, response);
	                  }
        		} else {
        			// Token not provided
        			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("token-error");
                    dispatcher.forward(request, response);
        		}
        		
        	}
            filterChain.doFilter(request, response);
        }
    }
}
