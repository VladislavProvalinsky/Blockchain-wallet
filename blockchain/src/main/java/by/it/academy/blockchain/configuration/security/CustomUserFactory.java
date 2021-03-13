package by.it.academy.blockchain.configuration.security;

import by.it.academy.blockchain.entity.Role;
import by.it.academy.blockchain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class CustomUserFactory {

    public static CustomUserImpl fromUserToCustomUser(User user) {
        return new CustomUserImpl(
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
