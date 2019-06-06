package ru.nikitamedvedev.application.service.converter;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.dto.*;
import ru.nikitamedvedev.application.service.dto.*;

@Component
@RequiredArgsConstructor
public class AssignmentTestBindingDbToAssignmentTestBindingConverter implements Converter<AssignmentTestBindingDb, AssignmentTestBinding> {

    private final Converter<TeacherUserDb, TeacherUser> teacherUserConverter;
    private final Converter<SubjectDb, Subject> subjectConverter;
    private final Converter<GroupDb, Group> groupConverter;
    private final Converter<AssignmentTestDb, AssignmentTest> assignmentTestConverter;
    private final Converter<SemesterDb, Semester> semesterConverter;

    @Override
    public AssignmentTestBinding convert(AssignmentTestBindingDb assignmentTestBindingDb) {
        val assignmentTestBinding = new AssignmentTestBinding();
        assignmentTestBinding.setId(assignmentTestBindingDb.getId());
        assignmentTestBinding.setAssignmentTest(assignmentTestConverter.convert(assignmentTestBindingDb.getAssignmentTest()));
        assignmentTestBinding.setCreated(teacherUserConverter.convert(assignmentTestBindingDb.getCreated()));
        assignmentTestBinding.setSemester(semesterConverter.convert(assignmentTestBindingDb.getSemester()));
        assignmentTestBinding.setGroup(groupConverter.convert(assignmentTestBindingDb.getGroup()));
        assignmentTestBinding.setSubject(subjectConverter.convert(assignmentTestBindingDb.getSubject()));
        assignmentTestBinding.setScores(assignmentTestBindingDb.getScores());
        assignmentTestBinding.setStarts(assignmentTestBindingDb.getStarts());
        assignmentTestBinding.setDuration(assignmentTestBindingDb.getDuration());
        return assignmentTestBinding;

    }
}
