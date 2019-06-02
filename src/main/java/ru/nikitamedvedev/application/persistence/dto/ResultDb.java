package ru.nikitamedvedev.application.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "result")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private TeacherUserDb user;
    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private AssignmentDb assignment;
    private Integer result;
    private ResultStatus status;
    @Column(columnDefinition = "varbinary(max)")
    private byte[] source;
    private String fileName;
}
