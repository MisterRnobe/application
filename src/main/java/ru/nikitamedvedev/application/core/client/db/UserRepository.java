package ru.nikitamedvedev.application.core.client.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nikitamedvedev.application.core.client.db.dto.UserDb;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDb, Long> {

    Optional<UserDb> findByLogin(String login);
}
