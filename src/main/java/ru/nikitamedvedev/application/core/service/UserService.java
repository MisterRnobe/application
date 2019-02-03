package ru.nikitamedvedev.application.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.core.client.db.GroupRepository;
import ru.nikitamedvedev.application.core.client.db.UserRepository;
import ru.nikitamedvedev.application.core.client.db.dto.GroupDb;
import ru.nikitamedvedev.application.core.client.db.dto.UserDb;
import ru.nikitamedvedev.application.core.hepler.PasswordGenerator;
import ru.nikitamedvedev.application.core.user.Authority;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;

    public List<Account> createGroupWithUsers(String groupName, List<String> names) {
        GroupDb groupDb = new GroupDb();
        groupDb.setName(groupName);
        GroupDb persistedGroup = groupRepository.save(groupDb);
        List<UserDb> users = names.stream()
                .map(name ->
                        UserDb.builder()
                                .login(passwordGenerator.generate(10)) // TODO: 03.02.2019 LOL
                                .authority(Authority.USER)
                                .name(name)
                                .groupDb(persistedGroup)
                                .password(passwordGenerator.generate(10))
                                .build())
                .collect(Collectors.toList());
        // TODO: 03.02.2019 Find out another way
        List<UserDb> withEncoded = users.stream()
                .peek(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
                .collect(Collectors.toList());
        userRepository.saveAll(withEncoded);
        return users.stream()
                .map(userDb ->
                        Account.builder()
                                .login(userDb.getLogin())
                                .name(userDb.getName())
                                .password(userDb.getPassword())
                                .build())
                .collect(Collectors.toList());
    }

}
