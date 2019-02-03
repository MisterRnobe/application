package ru.nikitamedvedev.application.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.core.client.db.UserRepository;

@Component
@RequiredArgsConstructor
public class UserProvider implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .map(userDb -> User.builder()
                        .login(userDb.getLogin())
                        .fullName(userDb.getName())
                        .password(userDb.getPassword())
                        .authority(userDb.getAuthority())
                        .groupName(userDb.getGroupDb().getName())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", username)));
    }
}
