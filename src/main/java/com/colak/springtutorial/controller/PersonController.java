package com.colak.springtutorial.controller;

import com.colak.springtutorial.jpa.Person;
import com.colak.springtutorial.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("person")

@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long id) {
        Optional<Person> optional = personService.findById(id);
        return optional
                .map(person -> {
                    PersonDto personDto = new PersonDto(person.getName(), person.getAge(), person.getEmail());
                    return new ResponseEntity<>(personDto, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
