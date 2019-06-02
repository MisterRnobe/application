package ru.nikitamedvedev.application.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.dto.*;
import ru.nikitamedvedev.application.service.dto.*;

@Component
@RequiredArgsConstructor
public class AssignmentBindingDbToAssignmentBindingConverter implements Converter<AssignmentBindingDb, AssignmentBinding> {

    private final Converter<TeacherUserDb, TeacherUser> teacherUserConverter;
    private final Converter<SubjectDb, Subject> subjectConverter;
    private final Converter<GroupDb, Group> groupConverter;
    private final Converter<AssignmentDb, Assignment> assignmentConverter;
    private final Converter<SemesterDb, Semester> semesterConverter;

    @Override
    public AssignmentBinding convert(AssignmentBindingDb assignmentBindingDb) {
        return new AssignmentBinding(
                assignmentConverter.convert(assignmentBindingDb.getAssignment()),
                teacherUserConverter.convert(assignmentBindingDb.getCreated()),
                semesterConverter.convert(assignmentBindingDb.getSemester()),
                groupConverter.convert(assignmentBindingDb.getGroup()),
                subjectConverter.convert(assignmentBindingDb.getSubject()),
                assignmentBindingDb.getScores(),
                assignmentBindingDb.getStarts()
        );
    }
}
