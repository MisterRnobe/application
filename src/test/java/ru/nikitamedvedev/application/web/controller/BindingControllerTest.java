package ru.nikitamedvedev.application.web.controller;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nikitamedvedev.application.Helper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class BindingControllerTest extends Helper {

    private Long assignmentId;
    private Long groupId;

    @SneakyThrows
    @Before
    public void init() {
        purgeDatabase();
        addUserWithoutGroup(SOME_LOGIN);
        groupId = addGroup(SOME_GROUP);
        assignmentId = addAssignment(ASSIGNMENT1);
    }

    @Test
    @SneakyThrows
    public void shouldBindExistingAssignmentToExistingGroup() {
        bindAssignmentToGroup(assignmentId, groupId, "2019-05-23T20:00:00Z", "2019-05-24T20:00:00Z", 10);

        mockMvc.perform(
                get("/binding/by-group/" + SOME_GROUP)
        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().json("{\n" +
                        "  \"assignments\": [\n" +
                        "    {\n" +
                        "      \"assignmentId\": " + assignmentId + ",\n" +
                        "      \"groupId\": " + groupId + ",\n" +
                        "      \"starts\": \"2019-05-23T20:00:00Z\",\n" +
                        "      \"finishes\": \"2019-05-24T20:00:00Z\",\n" +
                        "      \"maxScores\": \"10\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
        );
    }

    @Test
    @SneakyThrows
    public void shouldBindManyAssignments() {
        val ASSIGNMENT2 = "assignment2";
        val ASSIGNMENT3 = "assignment3";
        val assignmentId2 = addAssignment(ASSIGNMENT2);
        val assignmentId3 = addAssignment(ASSIGNMENT3);

        bindAssignmentToGroup(assignmentId, groupId, "2019-05-21T20:00:00Z", "2019-05-22T20:00:00Z", 10);
        bindAssignmentToGroup(assignmentId2, groupId, "2019-05-22T20:00:00Z", "2019-05-23T20:00:00Z", 9);
        bindAssignmentToGroup(assignmentId3, groupId, "2019-05-23T20:00:00Z", "2019-05-24T20:00:00Z", 8);

        mockMvc.perform(
                get("/binding/by-group/" + SOME_GROUP)
        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().json("{\n" +
                        "  \"assignments\": [\n" +
                        "    {\n" +
                        "      \"assignmentId\": " + assignmentId + ",\n" +
                        "      \"groupId\": " + groupId + ",\n" +
                        "      \"starts\": \"2019-05-21T20:00:00Z\",\n" +
                        "      \"finishes\": \"2019-05-22T20:00:00Z\",\n" +
                        "      \"maxScores\": \"10\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"assignmentId\": " + assignmentId2 + ",\n" +
                        "      \"groupId\": " + groupId + ",\n" +
                        "      \"starts\": \"2019-05-22T20:00:00Z\",\n" +
                        "      \"finishes\": \"2019-05-23T20:00:00Z\",\n" +
                        "      \"maxScores\": \"10\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"assignmentId\": " + assignmentId3 + ",\n" +
                        "      \"groupId\": " + groupId + ",\n" +
                        "      \"starts\": \"2019-05-23T20:00:00Z\",\n" +
                        "      \"finishes\": \"2019-05-24T20:00:00Z\",\n" +
                        "      \"maxScores\": \"10\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
        );
    }

}