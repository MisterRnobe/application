package ru.nikitamedvedev.application.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ASSIGNMENT_BINDING")
public class AssignmentBindingDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AssignmentDb assignment;
    @ManyToOne
    private GroupDb group;
    @ManyToOne
    private SemesterDb semester;
    @ManyToOne
    private SubjectDb subject;
    @ManyToOne
    private TeacherUserDb created;

    private LocalDate starts;
    private Integer scores;
}
