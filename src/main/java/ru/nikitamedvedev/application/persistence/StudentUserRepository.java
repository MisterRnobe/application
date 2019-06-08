package ru.nikitamedvedev.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.persistence.dto.StudentUserDb;

import java.util.List;

public interface StudentUserRepository extends JpaRepository<StudentUserDb, String> {

    List<StudentUserDb> findByGroup_Id(Long groupId);
}
