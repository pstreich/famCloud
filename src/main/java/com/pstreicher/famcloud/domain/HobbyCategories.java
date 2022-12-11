package com.pstreicher.famcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HobbyCategories {
    SPORTS("Sports"),
    GAMES("Games"),
    CREATIVITY("Creativity"),
    OUTDOOR("Outdoor"),
    TRAVEL("Travel"),
    READING("Reading"),
    FILMS_SERIES("Films & Series");

    private final String hobbyName;

}
