package ru.nikitamedvedev.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.persistence.dto.AssignmentTestBindingDb;

import java.util.List;

public interface AssignmentTestBindingRepository extends JpaRepository<AssignmentTestBindingDb, Long> {

    List<AssignmentTestBindingDb> findByCreated_Login(String login);

    List<AssignmentTestBindingDb> findByGroup_IdAndSemester_Id(Long groupId, Long semesterId);
}
