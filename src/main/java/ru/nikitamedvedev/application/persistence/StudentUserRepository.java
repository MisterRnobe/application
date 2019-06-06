package ru.nikitamedvedev.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.persistence.dto.StudentUserDb;

public interface StudentUserRepository extends JpaRepository<StudentUserDb, String> {
}
