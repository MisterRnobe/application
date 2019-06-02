package ru.nikitamedvedev.application.hepler;

import lombok.experimental.UtilityClass;

import javax.persistence.EntityNotFoundException;

@UtilityClass
public class ExceptionUtils {

    public static RuntimeException entityNotFound(String entity, Object id) {
        return new EntityNotFoundException(entity + " with id " + id + " was not found!");
    }
}
