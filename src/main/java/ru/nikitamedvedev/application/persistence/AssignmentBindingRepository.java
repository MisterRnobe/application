package ru.nikitamedvedev.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.persistence.dto.AssignmentBindingDb;

import java.util.List;

public interface AssignmentBindingRepository extends JpaRepository<AssignmentBindingDb, Long> {

    List<AssignmentBindingDb> findByCreated_Login(String login);

    List<AssignmentBindingDb> findByCreated_LoginAndSubject_Id(String login, Long subjectId);

    List<AssignmentBindingDb> findByGroup_IdAndSemester_Id(Long groupId, Long semesterId);

    List<AssignmentBindingDb> findByCreated_LoginAndGroup_Id(String login, Long groupId);
}
