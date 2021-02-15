package by.it.academy.blockchain.configuration.jwt;

import by.it.academy.blockchain.entity.Role;
import by.it.academy.blockchain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public static JwtUser fromUserToJwtUser(User user) {
        return new JwtUser(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthority(user.getRoles()));
    }

    private static Collection<? extends GrantedAuthority> mapRolesToAuthority(Set<Role> roles) {
        return roles.stream()
                .map(el -> new SimpleGrantedAuthority(el.getName()))
                .collect(Collectors.toList());
    }
}
