package ru.nikitamedvedev.application.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STUDENT")
public class StudentUserDb {

    @Id
    private String login;
    private String password;
    private String name;
    @ManyToOne
    private GroupDb group;


}
