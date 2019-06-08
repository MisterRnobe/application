package ru.nikitamedvedev.application.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitamedvedev.application.persistence.FileRepository;
import ru.nikitamedvedev.application.persistence.dto.FileDb;

import static ru.nikitamedvedev.application.hepler.ExceptionUtils.entityNotFound;

@Slf4j
@RestController
@RequestMapping(path = "/file")
@RequiredArgsConstructor
public class FileController {

    private final FileRepository fileRepository;

    @GetMapping(path = "/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable Long fileId) {
        FileDb file = fileRepository.findById(fileId).orElseThrow(() -> entityNotFound("file", fileId));
        val byteArrayResource = new ByteArrayResource(file.getFile(), file.getFileName());
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"")
                .contentLength(file.getFile().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(byteArrayResource);
    }
}
