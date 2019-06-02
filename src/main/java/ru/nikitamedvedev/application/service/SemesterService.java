package ru.nikitamedvedev.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.nikitamedvedev.application.persistence.SemesterRepository;
import ru.nikitamedvedev.application.persistence.dto.SemesterDb;
import ru.nikitamedvedev.application.service.dto.Semester;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final Converter<SemesterDb, Semester> converter;

    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll()
                .stream()
                .map(converter::convert)
                .collect(toList());
    }

    public void createSemester(String name) {
        semesterRepository.save(new SemesterDb(null, name));
    }
}
