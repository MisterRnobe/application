package ru.nikitamedvedev.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.persistence.dto.AssignmentBindingDb;

import java.util.List;

public interface AssignmentBindingRepository extends JpaRepository<AssignmentBindingDb, Long> {

    List<AssignmentBindingDb> findByCreated_Login(String login);

    List<AssignmentBindingDb> findByGroup_Id(Long groupId);
}
