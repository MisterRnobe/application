package ru.nikitamedvedev.application.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nikitamedvedev.application.persistence.dto.GroupDb;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<GroupDb, Long> {

    List<GroupDb> findAllBy();
}
