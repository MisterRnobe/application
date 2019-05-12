package ru.nikitamedvedev.application.core.service.converter;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.core.client.db.dto.GroupDb;
import ru.nikitamedvedev.application.core.client.db.dto.UserDb;
import ru.nikitamedvedev.application.core.service.dto.Group;
import ru.nikitamedvedev.application.core.service.dto.User;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDbToUserConverter implements Converter<UserDb, User> {

    private final Converter<GroupDb, Group> groupConverter;

    @Override
    public User convert(UserDb userDb) {
        val group = Optional.ofNullable(userDb.getGroupDb())
                .map(groupConverter::convert)
                .orElse(null);
        return new User(userDb.getLogin(), userDb.getName(), group);
    }
}
