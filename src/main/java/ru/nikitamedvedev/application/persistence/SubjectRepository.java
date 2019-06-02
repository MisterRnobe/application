package ru.nikitamedvedev.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitamedvedev.application.persistence.dto.SubjectDb;

import java.util.List;
import java.util.Set;

public interface SubjectRepository extends JpaRepository<SubjectDb, Long> {

    List<SubjectDb> findByIdIsNotIn(Set<Long> ids);

    List<SubjectDb> findByIdIn(Set<Long> ids);

}
