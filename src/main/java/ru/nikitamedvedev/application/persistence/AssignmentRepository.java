package ru.nikitamedvedev.application.persistence;

import org.springframework.data.repository.CrudRepository;
import ru.nikitamedvedev.application.persistence.dto.AssignmentDb;

import java.util.List;

public interface AssignmentRepository extends CrudRepository<AssignmentDb, Long> {

    List<AssignmentDb> findByName(String name);

    List<AssignmentDb> findByPosted_Login(String login);

}
