package ru.nikitamedvedev.application.core.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.core.client.db.dto.GroupDb;
import ru.nikitamedvedev.application.core.service.dto.Group;

@Component
public class GroupDbToGroupConverter implements Converter<GroupDb, Group> {
    @Override
    public Group convert(GroupDb groupDb) {
        return new Group(groupDb.getName());
    }
}
