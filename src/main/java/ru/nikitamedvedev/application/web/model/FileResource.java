package ru.nikitamedvedev.application.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResource {

    private Resource resource;
    private String fileName;

}
