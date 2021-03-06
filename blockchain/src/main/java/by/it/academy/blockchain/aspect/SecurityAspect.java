package by.it.academy.blockchain.aspect;


import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.service.UserService;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log
@Aspect
@Component
public class SecurityAspect {

    @Autowired
    UserService userService;

    @Autowired
    HttpServletResponse response;

    // pattern AOP
    // execution( modifiers-pattern? return-type-pattern declaring-type-pattern?
    //method-name-pattern(parameters-pattern)throws-pattern? )

//    @Pointcut("execution (* by.it.academy.blockchain.controller.SearchController.*(..))")
//    public void allMethodsForSearchControllerPointcut(){}
//
//    @Pointcut("execution (* by.it.academy.blockchain.controller.UserController.*(..))")
//    public void allMethodsForUserControllerPointcut(){}


    @Around("@annotation(by.it.academy.blockchain.aspect.SecureAuthorization)")
    public Object checkRequestForAuthAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[Calling SecureAuthorizationAdvice...]");
        HttpServletRequest servletRequest = currentRequest();
        Map<String, Long> map = (Map<String, Long>) servletRequest.getAttribute("org.springframework.web.servlet.View.pathVariables");
        Long id = map.entrySet().stream().findFirst().get().getValue();
        User user = userService.getOne(id);
        ModelAndView targetMethodResult;
        if (user != null && user.isActive()) {
            return joinPoint.proceed();
        } else {
            targetMethodResult = new ModelAndView();
            response.sendRedirect("/blockchain/notAuthorized");
            return targetMethodResult;
        }
    }


    private HttpServletRequest currentRequest() {
        // Use getRequestAttributes because of its return null if none bound
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(servletRequestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
    }
}
