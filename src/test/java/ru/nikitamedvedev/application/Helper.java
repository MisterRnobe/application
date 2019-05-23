package ru.nikitamedvedev.application;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.nikitamedvedev.application.core.client.db.AssignmentRepository;
import ru.nikitamedvedev.application.core.client.db.AssignmentTestRepository;
import ru.nikitamedvedev.application.core.client.db.UserRepository;
import ru.nikitamedvedev.application.core.client.db.dto.UserDb;

@SpringBootTest
@AutoConfigureMockMvc
public class Helper {

    @Autowired
    protected MockMvc mockMvc;

    protected static final String SOME_LOGIN = "some_login";

    @Autowired
    protected AssignmentRepository assignmentRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected AssignmentTestRepository assignmentTestRepository;

    protected void purgeDatabase() {
        assignmentRepository.deleteAll();
        assignmentTestRepository.deleteAll();
        userRepository.deleteAll();
    }

    protected void addUserWithoutGroup(String login) {
        val userDb = UserDb.builder()
                .login(login)
                .name(login)
                .build();
        userRepository.save(userDb);
    }
}
