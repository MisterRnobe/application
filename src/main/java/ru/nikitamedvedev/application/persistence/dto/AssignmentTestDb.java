package ru.nikitamedvedev.application.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "ASSIGNMENT_TEST")
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentTestDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    private List<QuestionDb> questions;
    @OneToOne
    private TeacherUserDb createdBy;

}
