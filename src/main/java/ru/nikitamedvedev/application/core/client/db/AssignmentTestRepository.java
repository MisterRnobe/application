package ru.nikitamedvedev.application.core.client.db;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.core.client.db.dto.AssignmentTestDb;

import java.util.List;

public interface AssignmentTestRepository extends JpaRepository<AssignmentTestDb, Long> {

    List<AssignmentTestDb> findByName(String name);

    List<AssignmentTestDb> findByCreatedBy_Login(String login);
}
