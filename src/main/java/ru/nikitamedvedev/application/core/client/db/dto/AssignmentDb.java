package ru.nikitamedvedev.application.core.client.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.core.client.db.converter.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "assignment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime starts;
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime finishes;
    private Integer maxScores;
    private String name;
    private byte[] source;

}
