package ru.nikitamedvedev.application.core.client.db.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = "id")
@Table(name = "assignment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @Convert(converter = LocalDateTimeConverter.class)
//    private LocalDateTime finishes;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private FileDb file;
    @ManyToOne
    private UserDb posted;
}
