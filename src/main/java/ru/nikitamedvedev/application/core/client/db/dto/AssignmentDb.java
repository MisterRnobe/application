package ru.nikitamedvedev.application.core.client.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.core.client.db.converter.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(columnDefinition = "varbinary(max)")
    private byte[] source;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "assignment_class",
            joinColumns = {@JoinColumn(name = "assignment_id")},
            inverseJoinColumns = {@JoinColumn(name = "class_id")}
    )
    private List<GroupDb> groups;

}
