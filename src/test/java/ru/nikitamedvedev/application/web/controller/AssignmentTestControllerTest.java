package ru.nikitamedvedev.application.web.controller;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nikitamedvedev.application.Helper;
import ru.nikitamedvedev.application.persistence.dto.AssignmentTestDb;
import ru.nikitamedvedev.application.persistence.dto.QuestionDb;
import ru.nikitamedvedev.application.persistence.dto.TeacherUserDb;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AssignmentTestControllerTest extends Helper {


    @Before
    public void init() {
        purgeDatabase();
    }

    @Test
    @SneakyThrows
    public void shouldCreateAssignment() {
        addUserWithoutGroup(SOME_LOGIN);
        mockMvc.perform(
                put("/assignment-test/create")
                        .param("postedBy", SOME_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        //language=JSON
                        .content("{\n" +
                                "  \"name\": \"test assignment\",\n" +
                                "  \"questions\": [\n" +
                                "    {\n" +
                                "      \"question\": \"question 1\",\n" +
                                "      \"badAnswers\": [\n" +
                                "        \"bad answer 1\",\n" +
                                "        \"bad answer 2\"\n" +
                                "      ],\n" +
                                "      \"goodAnswers\": [\n" +
                                "        \"good answer 1\",\n" +
                                "        \"good answer 2\"\n" +
                                "      ],\n" +
                                "      \"displayAnswers\": 4\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"question\": \"question 2\",\n" +
                                "      \"badAnswers\": [\n" +
                                "        \"bad answer 3\",\n" +
                                "        \"bad answer 4\",\n" +
                                "        \"bad answer 5\"\n" +
                                "      ],\n" +
                                "      \"goodAnswers\": [\n" +
                                "        \"good answer 3\"\n" +
                                "      ],\n" +
                                "      \"displayAnswers\": 3\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}")

        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        );

        val assignmentDbs = assignmentTestRepository.findAll();
        val assignmentTestDb = assignmentDbs.get(0);

        assertEquals(assignmentDbs.size(), 1);
        assertEquals("test assignment", assignmentTestDb.getName());
        assertEquals(TeacherUserDb.builder().login(SOME_LOGIN).name(SOME_LOGIN).build(), assignmentTestDb.getCreatedBy());
        assertEquals(Arrays.asList(
                new QuestionDb(null, "question 1", Arrays.asList("bad answer 1", "bad answer 2"), Arrays.asList("good answer 1", "good answer 2"), 4),
                new QuestionDb(null, "question 2", Arrays.asList("bad answer 3", "bad answer 4", "bad answer 5"), Arrays.asList("good answer 3"), 3)
        ), assignmentTestDb.getQuestions());
    }

    @Test
    @SneakyThrows
    public void shouldFindAllBelongingToGivenUserId() {
        shouldCreateAssignment();

        val assignment = assignmentTestRepository.findByName("test assignment").stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        mockMvc.perform(
                get("/assignment-test/get-by-creator/" + SOME_LOGIN)
        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        ).andExpect(
                //language=JSON
                content().json("[\n" +
                        "  {\n" +
                        "    \"assignmentId\": " + assignment.getAssignmentId() + ",\n" +
                        "    \"name\": \"test assignment\",\n" +
                        "    \"questions\": [\n" +
                        "      {\n" +
                        "        \"question\": \"question 1\",\n" +
                        "        \"badAnswers\": [\n" +
                        "          \"bad answer 1\",\n" +
                        "          \"bad answer 2\"\n" +
                        "        ],\n" +
                        "        \"goodAnswers\": [\n" +
                        "          \"good answer 1\",\n" +
                        "          \"good answer 2\"\n" +
                        "        ],\n" +
                        "        \"displayAnswers\": 4\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"question\": \"question 2\",\n" +
                        "        \"badAnswers\": [\n" +
                        "          \"bad answer 3\",\n" +
                        "          \"bad answer 4\",\n" +
                        "          \"bad answer 5\"\n" +
                        "        ],\n" +
                        "        \"goodAnswers\": [\n" +
                        "          \"good answer 3\"\n" +
                        "        ],\n" +
                        "        \"displayAnswers\": 3\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "]")
        );

    }

    @Test
    @SneakyThrows
    public void shouldModifyAssignment() {
        shouldCreateAssignment();

        val assignmentId = assignmentTestRepository.findByName("test assignment").stream()
                .map(AssignmentTestDb::getAssignmentId)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        mockMvc.perform(
                post("/assignment-test/modify/" + assignmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        //language=JSON
                        .content("{\n" +
                                "  \"name\": \"test assignment 2\",\n" +
                                "  \"questions\": [\n" +
                                "    {\n" +
                                "      \"question\": \"question 3\",\n" +
                                "      \"badAnswers\": [\n" +
                                "        \"bad answer 7\",\n" +
                                "        \"bad answer 8\",\n" +
                                "        \"bad answer 9\"\n" +
                                "      ],\n" +
                                "      \"goodAnswers\": [\n" +
                                "        \"good answer 0\"\n" +
                                "      ],\n" +
                                "      \"displayAnswers\": 2\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}")

        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        );

        val actual = assignmentTestRepository.findById(assignmentId).get();

        assertEquals("test assignment 2", actual.getName());
        assertEquals(TeacherUserDb.builder().login(SOME_LOGIN).name(SOME_LOGIN).build(), actual.getCreatedBy());
        assertEquals(Collections.singletonList(
                new QuestionDb(null, "question 3", Arrays.asList("bad answer 7", "bad answer 8", "bad answer 9"), Collections.singletonList("good answer 0"), 2)
        ), actual.getQuestions());
    }

    @Test
    @SneakyThrows
    public void shouldRemoveAssignment() {
        shouldCreateAssignment();

        val assignmentId = assignmentTestRepository.findByName("test assignment").stream()
                .map(AssignmentTestDb::getAssignmentId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        mockMvc.perform(
                delete("/assignment-test/delete/" + assignmentId)
        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        );

        assertEquals(Optional.empty(), assignmentRepository.findById(assignmentId));
    }

    @Test
    @SneakyThrows
    public void shouldRemoveEvenIfBindingsExist() {
        // TODO: 12.05.2019 Implement
        fail();
    }


}