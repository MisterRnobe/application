package ru.nikitamedvedev.application.core.client.db;

import org.springframework.data.repository.CrudRepository;
import ru.nikitamedvedev.application.core.client.db.dto.AssignmentDb;

public interface AssignmentRepository extends CrudRepository<AssignmentDb, Long> {
}
