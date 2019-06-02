package ru.nikitamedvedev.application.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.dto.GroupDb;
import ru.nikitamedvedev.application.service.dto.Group;

@Component
public class GroupDbToGroupConverter implements Converter<GroupDb, Group> {
    @Override
    public Group convert(GroupDb groupDb) {
        return new Group(groupDb.getId(), groupDb.getName());
    }
}
