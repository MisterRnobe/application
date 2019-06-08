package ru.nikitamedvedev.application.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.service.dto.Status;

import javax.persistence.*;

@Data
@Entity
@Table(name = "result")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentResultDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AssignmentBindingDb assignmentBinding;

    @ManyToOne
    private StudentUserDb created;
    @OneToOne(cascade = CascadeType.ALL)
    private FileDb file;
    @Column(columnDefinition = "text")
    private String comment;

    private Integer scores;
    private Status status;

}
