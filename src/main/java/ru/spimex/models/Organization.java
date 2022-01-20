package ru.spimex.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class Organization {
    private final String id;
    private final String name;
    private final String inn;
    private final String residence;
    private final Date storeDate;
    private final Date blockDate;
}
