package ru.nikitamedvedev.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ObjectMapperTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldConvertToStringList() throws IOException {
        //language=JSON
        String raw = "[\"a\", \"b\"]";

        List<Object> parsed = objectMapper.readValue(raw, ArrayList.class);
        List<String> names = parsed.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        assertEquals(2, parsed.size());
        assertTrue(names.contains("a"));
        assertTrue(names.contains("b"));
    }
}
