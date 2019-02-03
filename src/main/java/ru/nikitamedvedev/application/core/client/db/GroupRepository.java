package ru.nikitamedvedev.application.core.client.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nikitamedvedev.application.core.client.db.dto.GroupDb;

import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<GroupDb, Long> {

    Optional<GroupDb> findByName(String name);
}
