package ru.nikitamedvedev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitamedvedev.application.persistence.AssignmentTestRepository;
import ru.nikitamedvedev.application.persistence.TeacherUserRepository;
import ru.nikitamedvedev.application.persistence.dto.AssignmentTestDb;
import ru.nikitamedvedev.application.persistence.dto.QuestionDb;
import ru.nikitamedvedev.application.service.converter.AssignmentTestDbToAssignmentTestConverter;
import ru.nikitamedvedev.application.service.dto.AssignmentTest;
import ru.nikitamedvedev.application.service.dto.Question;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentTestService {

    private final AssignmentTestDbToAssignmentTestConverter converter;
    private final AssignmentTestRepository assignmentTestRepository;
    private final TeacherUserRepository teacherUserRepository;

    @Transactional
    public void createAssignment(String name, List<Question> questions, String postedBy) {
        val questionDbs = convertQuestions(questions);

        val saved = assignmentTestRepository.save(new AssignmentTestDb(null, name, questionDbs, teacherUserRepository.findById(postedBy).orElseThrow(() -> new RuntimeException("TeacherUser " + postedBy + " not found"))));
        log.info("Created: {}", saved);
    }

    public void modifyAssignment(Long id, String name, List<Question> questions) {
        val questionDbs = convertQuestions(questions);
        val assignmentTestDb = assignmentTestRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Assignment test with id " + id + " not found"));
        assignmentTestDb.setName(name);
        assignmentTestDb.setQuestions(questionDbs);
        val saved = assignmentTestRepository.save(assignmentTestDb);
        log.info("Updated: {}", saved);
    }

    private List<QuestionDb> convertQuestions(List<Question> questions) {
        return questions.stream()
                .map(question -> new QuestionDb(null, question.getQuestion(), question.getBadAnswers(), question.getGoodAnswers(), question.getDisplayAnswers()))
                .collect(Collectors.toList());
    }

    public List<AssignmentTest> getByCreator(String login) {
        return assignmentTestRepository.findByCreatedBy_Login(login).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public void deleteAssignment(Long id) {
        assignmentTestRepository.deleteById(id);
    }
}
