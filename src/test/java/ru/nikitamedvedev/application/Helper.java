package ru.nikitamedvedev.application;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.nikitamedvedev.application.persistence.AssignmentRepository;
import ru.nikitamedvedev.application.persistence.GroupRepository;
import ru.nikitamedvedev.application.persistence.TeacherUserRepository;
import ru.nikitamedvedev.application.persistence.dto.AssignmentDb;
import ru.nikitamedvedev.application.persistence.dto.FileDb;
import ru.nikitamedvedev.application.persistence.dto.GroupDb;
import ru.nikitamedvedev.application.persistence.dto.TeacherUserDb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class Helper {

    @Autowired
    protected MockMvc mockMvc;

    protected static final String SOME_LOGIN = "some_login";
    protected static final String SOME_GROUP = "some_group";
    protected static final String ASSIGNMENT1 = "assignment1";

    @Autowired
    protected AssignmentRepository assignmentRepository;
    @Autowired
    protected TeacherUserRepository teacherUserRepository;
    @Autowired
    protected GroupRepository groupRepository;

    protected void purgeDatabase() {
        assignmentRepository.deleteAll();
        teacherUserRepository.deleteAll();

        groupRepository.deleteAll();
    }

    protected void addUserWithoutGroup(String login) {
        val userDb = TeacherUserDb.builder()
                .login(login)
                .name(login)
                .build();
        teacherUserRepository.save(userDb);
    }

    protected Long addGroup(String group) {
        return groupRepository.save(new GroupDb(null, group)).getId();
    }

    protected Long addAssignment(String name) {
        val assignmentDb = AssignmentDb.builder()
                .name(name)
                .posted(teacherUserRepository.findById(SOME_LOGIN).get())
                .file(new FileDb(null, "file.txt", "blah-blah".getBytes()))
                .build();
        return assignmentRepository.save(assignmentDb).getId();
    }

    @SneakyThrows
    protected void bindAssignmentToGroup(Long assignmentId, Long groupId, String starts, String finishes, Integer maxScores) {
        mockMvc.perform(
                put("/binding/create")
                        //language=JSON
                        .content("{\n" +
                                "  \"assignmentId\": " + assignmentId + ",\n" +
                                "  \"groupId\": " + groupId + ",\n" +
                                "  \"starts\": \"" + starts + "\",\n" +
                                "  \"finishes\": \"" + finishes + "\",\n" +
                                "  \"maxScores\": \"" + maxScores + "\"\n" +
                                "}")

        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        );
    }
}
