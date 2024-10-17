package com.colak.springtutorial.service;

import com.colak.springtutorial.jpa.Person;
import com.colak.springtutorial.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    @Transactional(readOnly = true)
    public Optional<Person> findById(Long id) {
        return repository.findById(id);
    }
}
