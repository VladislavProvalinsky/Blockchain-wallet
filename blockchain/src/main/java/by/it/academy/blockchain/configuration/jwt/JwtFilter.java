//package by.it.academy.blockchain.configuration.jwt;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import lombok.Getter;
//import lombok.extern.java.Log;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.annotation.PostConstruct;
//import javax.naming.AuthenticationException;
//import javax.naming.directory.NoSuchAttributeException;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Log
//@Getter
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    public static final String AUTHORIZATION = "Authorization";
//    private List<String> excludedUrls;
//    private volatile boolean isSkippedURI;
//
//    @Autowired
//    private JwtProvider jwtProvider;
//
//    @Autowired
//    JwtUserDetailsService jwtUserDetailsService;
//
////    @Autowired
////    AuthenticationManager authenticationManager;
//
//    @PostConstruct
//    public void init() {
//        excludedUrls = new ArrayList<>() {{
//            add("/blockchain/");
//            add("/blockchain/registration/");
//            add("/blockchain/registration/new_user/");
//            add("/blockchain/login/");
//        }};
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        isSkippedURI = excludedUrls.stream().anyMatch(p -> p.equals(request.getRequestURI()));
//        return isSkippedURI;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (!isSkippedURI) filterChain.doFilter(request, response);
//        else {
//            log.info("Calling JwtFilter..........");
//            String token = getTokenFromRequest(request);
//            try {
//                if (token == null) {
//                    try {
//                        token = authenticateUser(request);
//                        response.addCookie(new Cookie(AUTHORIZATION, token));
//                    } catch (AuthenticationException e) {
//                        throw new JwtAuthenticationException(e.getMessage());
//                    } catch (NoSuchAttributeException ignored) {
//                        log.warning("No attribute in request for authentication!");
//                    }
//                }
//                log.info("token - " + token);
//                if (token != null && jwtProvider.validateToken(token)) {
//                    log.info("Token correct!!!!!!!!!!");
//                    String userLogin = jwtProvider.getLoginFromToken(token);
//                    List<SimpleGrantedAuthority> rolesFromToken = jwtProvider.getRolesFromToken(token);
//                    JwtUser jwtUser = new JwtUser(userLogin, "", rolesFromToken);
//                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(auth);
//                } else log.warning("Cannot set the SecurityContextHolder!");
//            } catch (ExpiredJwtException ex) {
//                String isRefreshToken = request.getHeader("isRefreshToken");
//                String requestURL = request.getRequestURL().toString();
//                // allow for Refresh Token creation if following conditions are true.
//                if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
//                    allowForRefreshToken(ex, request);
//                } else
//                    request.setAttribute("exception", ex);
//            } catch (JwtAuthenticationException | UnsupportedJwtException | MalformedJwtException | SignatureException ex) {
//                request.setAttribute("exception", ex);
//            } finally {
//                filterChain.doFilter(request, response);
//            }
//        }
//    }
//
//
//    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
//        // create a UsernamePasswordAuthenticationToken with null values.
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                null, null, null);
//        // After setting the Authentication in the context, we specify
//        // that the current user is authenticated. So it passes the
//        // Spring Security Configurations successfully.
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//        // Set the claims so that in controller we will be using it to create
//        // new JWT
//        request.setAttribute("claims", ex.getClaims());
//
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        log.info("Getting token from request..........");
//
//        String token = null;
//        Cookie[] cookies = request.getCookies();
//        for (Cookie c : cookies) {
//            if (c.getName().equals(AUTHORIZATION)) {
//                token = c.getValue();
//            }
//        }
//
////        String bearer = request.getHeader(AUTHORIZATION);
////        if (bearer != null) {
////            if (hasText(bearer) && bearer.startsWith("Bearer "))
////                return bearer.substring(7);
////
////        }
//        return token;
//    }
//
//    private String authenticateUser(HttpServletRequest request) throws NoSuchAttributeException, AuthenticationException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        if (username == null && password == null) {
//            throw new NoSuchAttributeException();
//        } else {
////            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( // продебажить работу тк хз дергает он из базы юзера или
////                    username, password));// нет
////            if (authentication.isAuthenticated()) {
////                JwtUser jwtUser = jwtUserDetailsService.loadUserByUsername(username);
////                return jwtProvider.generateToken(jwtUser);
////            }
//        }
//        throw new AuthenticationException();
//    }
//
//}
