package com.colak.springtutorial.jpa;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;


@Entity
@Table(name = "person")

@Getter
public class Person {
    @Id
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
