package ru.nikitamedvedev.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.persistence.dto.SemesterDb;

public interface SemesterRepository extends JpaRepository<SemesterDb, Long> {
}
