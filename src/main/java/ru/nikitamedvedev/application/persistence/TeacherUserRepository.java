package ru.nikitamedvedev.application.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nikitamedvedev.application.persistence.dto.StudentUserDb;
import ru.nikitamedvedev.application.persistence.dto.TeacherUserDb;

import java.util.Optional;

@Repository
public interface TeacherUserRepository extends CrudRepository<TeacherUserDb, String> {

    Optional<TeacherUserDb> findByLoginAndPassword(String login, String password);

}
