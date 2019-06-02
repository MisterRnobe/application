package ru.nikitamedvedev.application.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.dto.SubjectDb;
import ru.nikitamedvedev.application.service.dto.Subject;

@Component
public class SubjectDbToSubjectConverter implements Converter<SubjectDb, Subject> {
    @Override
    public Subject convert(SubjectDb subjectDb) {
        return new Subject(subjectDb.getId(), subjectDb.getSubjectName());
    }
}
