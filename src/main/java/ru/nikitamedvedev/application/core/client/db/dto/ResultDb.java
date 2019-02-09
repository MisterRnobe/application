package ru.nikitamedvedev.application.core.client.db.dto;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "result")
public class ResultDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDb user;
    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private AssignmentDb assignmentDb;
    private Long result;
}
