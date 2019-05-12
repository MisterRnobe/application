package ru.nikitamedvedev.application.core.client.db;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.core.client.db.dto.FileDb;

public interface FileRepository extends JpaRepository<FileDb, Long> {
}
