package ru.nikitamedvedev.application.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nikitamedvedev.application.persistence.dto.TeacherUserDb;

@Repository
public interface TeacherUserRepository extends CrudRepository<TeacherUserDb, String> {

}
