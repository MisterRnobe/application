package ru.nikitamedvedev.application.core.client.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nikitamedvedev.application.core.client.db.dto.ResultDb;
import ru.nikitamedvedev.application.core.client.db.dto.ResultStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends CrudRepository<ResultDb, Long> {

}
