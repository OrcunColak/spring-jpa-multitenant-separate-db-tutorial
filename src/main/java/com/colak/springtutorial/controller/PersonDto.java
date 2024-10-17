package com.colak.springtutorial.controller;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PersonDto {
    private String name;
    private Integer age;
    private String email;
}
