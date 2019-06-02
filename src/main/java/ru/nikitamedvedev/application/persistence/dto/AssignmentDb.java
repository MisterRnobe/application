package ru.nikitamedvedev.application.persistence.dto;

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
    private TeacherUserDb posted;
}
