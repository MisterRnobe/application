package ru.nikitamedvedev.application.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.dto.TeacherUserDb;
import ru.nikitamedvedev.application.service.dto.TeacherUser;

@Component
@RequiredArgsConstructor
public class TeacherUserDbToTeacherUserConverter implements Converter<TeacherUserDb, TeacherUser> {

    @Override
    public TeacherUser convert(TeacherUserDb teacherUserDb) {
        return new TeacherUser(teacherUserDb.getLogin(), teacherUserDb.getName());
    }
}
