package com.MrSurenK.SpendSense_BackEnd.configuration;

import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //Inject dependencies in constructor
    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService,
                                   HandlerExceptionResolver handlerExceptionResolver){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        //get the auth information from request headers
        final String authHeader = request.getHeader("Authorization");

        //If no auth Header or Bearer token present then exit this filter and go to the next filter
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        //Get user account details and put in Security Context
        try {
            //Get jwt from header and from it get username to pass to usernamePasswordAuthenticationToken
            final String jwtToken = authHeader.substring(7);
            final String username = jwtService.extractUsername(jwtToken);

            //Check if authentication object is already stored in security context, if so we do not want to overwrite
            //This would mean user was authenticated before
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(username != null && authentication == null){
                //Now we confirm that user is not authenticated before we get user info from db
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //Get any authorities from JWT token
                if(jwtService.isTokenValid(jwtToken,userDetails)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,
                                    userDetails.getAuthorities()); //currently no authorities, but if there are we want to set it in security context

                    //Add some extra meta data details into authToken like remote address and session id
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            //If all is successful, move on to the next filter
            filterChain.doFilter(request,response);

        } catch (Exception e){
            handlerExceptionResolver.resolveException(request, response, null, e);
        }





    }

}
