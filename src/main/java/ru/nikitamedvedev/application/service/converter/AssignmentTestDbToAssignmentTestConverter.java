package ru.nikitamedvedev.application.service.converter;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.dto.AssignmentTestDb;
import ru.nikitamedvedev.application.persistence.dto.QuestionDb;
import ru.nikitamedvedev.application.service.dto.AssignmentTest;
import ru.nikitamedvedev.application.service.dto.Question;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssignmentTestDbToAssignmentTestConverter implements Converter<AssignmentTestDb, AssignmentTest> {

    private final TeacherUserDbToTeacherUserConverter userConverter;

    @Override
    public AssignmentTest convert(AssignmentTestDb assignmentTestDb) {
        val assignmentTest = new AssignmentTest();
        assignmentTest.setAssignmentId(assignmentTestDb.getAssignmentId());
        assignmentTest.setCreatedBy(userConverter.convert(assignmentTestDb.getCreatedBy()));
        assignmentTest.setName(assignmentTestDb.getName());
        assignmentTest.setQuestions(convertQuestions(assignmentTestDb.getQuestions()));
        return assignmentTest;
    }

    private List<Question> convertQuestions(List<QuestionDb> questions) {
        return questions.stream()
                .map(questionDb -> new Question(questionDb.getQuestion(), questionDb.getBadAnswers(), questionDb.getGoodAnswers(), questionDb.getDisplayAnswers()))
                .collect(Collectors.toList());
    }
}
