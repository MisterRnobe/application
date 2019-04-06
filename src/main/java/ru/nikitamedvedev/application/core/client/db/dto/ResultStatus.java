package ru.nikitamedvedev.application.core.client.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultStatus {

    NOT_UPLOADED("Не загружена"),
    NEW("Не просмотрена"),
    REFINEMENT("Требуется доработка"),
    ACCEPTED("Требуется защита"),
    COMPLETED("Сдана");

    private String description;

}
