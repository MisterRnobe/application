package ru.nikitamedvedev.application.web.controller;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nikitamedvedev.application.Helper;
import ru.nikitamedvedev.application.core.client.db.dto.AssignmentDb;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AssignmentControllerTest extends Helper {

    @Before
    public void init() {
        purgeDatabase();
    }

    @Test
    @SneakyThrows
    public void shouldCreateAssignment() {
        addUserWithoutGroup("user1");
        val file = new MockMultipartFile("file", "filename.txt", null, "file content".getBytes());

        mockMvc.perform(
                multipart("/assignment/create")
                        .file(file)
                        .param("name", "assignment1")
                        .param("postedBy", "user1")

        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        );

        val assignmentDbs = assignmentRepository.findByName("assignment1");
        val actual = assignmentDbs.get(0);

        assertEquals(assignmentDbs.size(), 1);
        assertEquals("assignment1", actual.getName());
        assertEquals("filename.txt", actual.getFile().getFileName());
        assertEquals("file content", new String(actual.getFile().getFile()));
        assertEquals("user1", actual.getPosted().getLogin());
    }

    @Test
    @SneakyThrows
    public void shouldFindAllBelongingToGivenUserId() {
        shouldCreateAssignment();

        val assignment = assignmentRepository.findByName("assignment1").stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        mockMvc.perform(
                get("/assignment/get-by-creator/user1")
        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        ).andExpect(
                //language=JSON
                content().json("[\n" +
                        "  {\n" +
                        "    \"assignmentId\": " + assignment.getId() + ",\n" +
                        "    \"name\": \"assignment1\",\n" +
                        "    \"fileId\": " + assignment.getFile().getId() + ",\n" +
                        "    \"createdBy\": {\n" +
                        "      \"login\": \"user1\",\n" +
                        "      \"name\": \"user1\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "]")
        );

    }

    @Test
    @SneakyThrows
    public void shouldModifyAssignmentName() {
        shouldCreateAssignment();

        val assignmentId = assignmentRepository.findByName("assignment1").stream()
                .map(AssignmentDb::getId)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        mockMvc.perform(
                post("/assignment/modify/" + assignmentId)
                        .param("name", "assignment2")

        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        );

        val assignmentDbs = assignmentRepository.findByName("assignment2");
        val actual = assignmentDbs.get(0);

        assertEquals(assignmentDbs.size(), 1);
        assertEquals("assignment2", actual.getName());
        assertEquals("filename.txt", actual.getFile().getFileName());
        assertEquals("file content", new String(actual.getFile().getFile()));
        assertEquals("user1", actual.getPosted().getLogin());
    }

    @Test
    @SneakyThrows
    public void shouldModifyAssignmentContent() {
        shouldCreateAssignment();

        val assignmentId = assignmentRepository.findByName("assignment1").stream()
                .map(AssignmentDb::getId)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);


        val file = new MockMultipartFile("file", "new filename.txt", null, "new file content".getBytes());

        mockMvc.perform(
                multipart("/assignment/modify/" + assignmentId)
                        .file(file)

        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        );

        val assignmentDbs = assignmentRepository.findByName("assignment1");
        val actual = assignmentDbs.get(0);

        assertEquals(assignmentDbs.size(), 1);
        assertEquals("assignment1", actual.getName());
        assertEquals("new filename.txt", actual.getFile().getFileName());
        assertEquals("new file content", new String(actual.getFile().getFile()));
        assertEquals("user1", actual.getPosted().getLogin());
    }

    @Test
    @SneakyThrows
    public void shouldRemoveAssignment() {
        shouldCreateAssignment();

        val assignmentId = assignmentRepository.findByName("assignment1").stream()
                .map(AssignmentDb::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        mockMvc.perform(
                delete("/assignment/delete/" + assignmentId)

        ).andDo(
                print()
        ).andExpect(
                status().isOk()
        );

        assertEquals(Optional.empty(), assignmentRepository.findById(assignmentId));
    }

    @Test
    @SneakyThrows
    public void shouldNotRemoveIfBindingsExist() {
        // TODO: 12.05.2019 Implement
        fail();
    }
}