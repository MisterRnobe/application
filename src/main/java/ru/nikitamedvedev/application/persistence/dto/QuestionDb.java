package ru.nikitamedvedev.application.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "QUESTION")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class QuestionDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    @ElementCollection
    private List<String> badAnswers;
    @ElementCollection
    private List<String> goodAnswers;
    private Integer displayAnswers;

}
