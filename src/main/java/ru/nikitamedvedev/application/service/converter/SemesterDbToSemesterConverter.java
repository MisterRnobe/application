package ru.nikitamedvedev.application.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.dto.SemesterDb;
import ru.nikitamedvedev.application.service.dto.Semester;

@Component
public class SemesterDbToSemesterConverter implements Converter<SemesterDb, Semester> {
    @Override
    public Semester convert(SemesterDb semesterDb) {
        return new Semester(semesterDb.getId(), semesterDb.getName());
    }
}
