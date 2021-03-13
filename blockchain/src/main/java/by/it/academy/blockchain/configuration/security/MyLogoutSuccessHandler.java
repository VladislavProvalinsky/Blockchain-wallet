package by.it.academy.blockchain.configuration.security;

import by.it.academy.blockchain.entity.User;
import by.it.academy.blockchain.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Getter
@Setter
@Log
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    UserRepository userRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {

        log.info("[Calling LogoutSuccessHandler....]");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User tempUser = userRepository.findByUsername(userDetails.getUsername());
        tempUser.setActive(false);
        userRepository.save(tempUser);

        super.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}
