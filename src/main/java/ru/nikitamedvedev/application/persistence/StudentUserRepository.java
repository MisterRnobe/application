package ru.nikitamedvedev.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.persistence.dto.StudentUserDb;

import java.util.List;
import java.util.Optional;

public interface StudentUserRepository extends JpaRepository<StudentUserDb, String> {

    List<StudentUserDb> findByGroup_Id(Long groupId);

    Optional<StudentUserDb> findByLoginAndPassword(String login, String password);
}
