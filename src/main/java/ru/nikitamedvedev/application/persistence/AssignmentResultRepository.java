package ru.nikitamedvedev.application.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nikitamedvedev.application.persistence.dto.AssignmentResultDb;
import ru.nikitamedvedev.application.service.dto.Status;

import java.util.List;

@Repository
public interface AssignmentResultRepository extends CrudRepository<AssignmentResultDb, Long> {

    List<AssignmentResultDb> findByCreated_Login(String login);

    List<AssignmentResultDb> findByCreated_LoginAndAssignmentBinding_Id(String login, Long bindingId);

    List<AssignmentResultDb> findByStatusAndAssignmentBinding_Created_Login(Status status, String login);
}
