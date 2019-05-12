package ru.nikitamedvedev.application.core.client.db.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "class")
public class GroupDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @ManyToMany(mappedBy = "groups")
//    private List<AssignmentDb> assignments;
}
