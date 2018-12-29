package nz.co.orthodocx.repository.mongodb.reactive;

import nz.co.orthodocx.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

public class ReactiveProfileRepositoryTest {

    @Autowired
    private ReactiveProfileRepository repository;

    @Test
    public void findByFirstname() {
    }

    @Test
    public void findByFirstnameOrderByLastname() {
        Flux<Profile> actual = repository.findAll(Sort.by(new Sort.Order(ASC, "lastname")));
    }

    @Test
    public void findByFirstnameAndLastname() {
    }

    @Test
    public void findFirstByLastname() {
    }
}