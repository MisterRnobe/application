package ru.nikitamedvedev.application.core.client.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDb {
    @Id
    private String login;
    private String name;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupDb groupDb;
}
