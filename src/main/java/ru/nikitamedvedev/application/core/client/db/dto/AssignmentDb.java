package ru.nikitamedvedev.application.core.client.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "assignment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @Convert(converter = LocalDateTimeConverter.class)
//    private LocalDateTime starts;
//    @Convert(converter = LocalDateTimeConverter.class)
//    private LocalDateTime finishes;
    private String name;
    private String fileName;
    private byte[] file;
//    @Column(columnDefinition = "varbinary(max)")
//    @OneToOne(cascade = {CascadeType.ALL})
//    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(
//            name = "assignment_class",
//            joinColumns = {@JoinColumn(name = "assignment_id")},
//            inverseJoinColumns = {@JoinColumn(name = "class_id")}
//    )
//    private List<GroupDb> groups;

}
