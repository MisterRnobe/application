package ru.nikitamedvedev.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.persistence.dto.FileDb;

public interface FileRepository extends JpaRepository<FileDb, Long> {
}
