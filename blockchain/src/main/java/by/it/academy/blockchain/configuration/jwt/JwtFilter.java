package by.it.academy.blockchain.configuration.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Component
@Log
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("do filter...");
        log.info("Calling JwtFilter..........");
        String token = getTokenFromRequest(request, response);
        if (token == null) authenticateUser(request, response);
        try {
            log.info("token - " + token);
            if (token != null && jwtProvider.validateToken(token)) {
                log.info("Token correct!!!!!!!!!!");
                String userLogin = jwtProvider.getLoginFromToken(token);
                List<SimpleGrantedAuthority> rolesFromToken = jwtProvider.getRolesFromToken(token);
                JwtUser jwtUser = new JwtUser(userLogin, "", rolesFromToken);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else log.warning("Cannot set the SecurityContextHolder!");
        } catch (ExpiredJwtException ex) {
            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
                allowForRefreshToken(ex, request);
            } else
                request.setAttribute("exception", ex);
        } catch (JwtAuthenticationException ex) {
            request.setAttribute("exception", ex);
        }
        chain.doFilter(request, response);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());

    }

    private String getTokenFromRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("Getting token from request..........");
        String result = null;
        String bearer = request.getHeader(AUTHORIZATION);
        String bearer2 = response.getHeader(AUTHORIZATION);
        if (bearer != null) {
            if (hasText(bearer) && bearer.startsWith("Bearer ")) result = bearer.substring(7);
            else result = null;
        }
        if (bearer2 != null) {
            if (hasText(bearer2) && bearer2.startsWith("Bearer ")) result = bearer2.substring(7);
            else result = null;
        }
        return result;
    }

    private boolean authenticateUser(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getAttribute("username");
        String password = (String) request.getAttribute("password");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, password));
        } catch (DisabledException | BadCredentialsException e) {
            return false;
        }
        JwtUser jwtUser = jwtUserDetailsService.loadUserByUsername(username);
        String token = jwtProvider.generateToken(jwtUser);
        response.addHeader("AUTHORIZATION", token);
        return true;
    }

}
