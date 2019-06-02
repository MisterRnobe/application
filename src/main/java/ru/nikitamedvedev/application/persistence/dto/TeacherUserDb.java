package ru.nikitamedvedev.application.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TEACHER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherUserDb {
    @Id
    private String login;
    private String password;
    private String name;
    @ManyToMany
    private List<SubjectDb> subjects;
}
