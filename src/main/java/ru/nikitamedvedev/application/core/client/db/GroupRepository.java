package ru.nikitamedvedev.application.core.client.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nikitamedvedev.application.core.client.db.dto.GroupDb;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<GroupDb, Long> {

    List<GroupDb> findByNameIn(List<String> names);
}
