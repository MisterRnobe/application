package ru.nikitamedvedev.application.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nikitamedvedev.application.persistence.dto.ResultDb;

@Repository
public interface ResultRepository extends CrudRepository<ResultDb, Long> {

}
