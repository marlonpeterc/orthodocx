package nz.co.orthodocx.service;

import lombok.extern.slf4j.Slf4j;
import nz.co.orthodocx.annotation.LogExecutionTime;
import nz.co.orthodocx.model.Profile;
import nz.co.orthodocx.repository.mongodb.reactive.ProfileCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProfileService {

    @Autowired
    private ProfileCrudRepository crudRepository;

    @LogExecutionTime
    public Flux<Profile> findByFirstname(String firstname) {
        return crudRepository.findByFirstname(firstname);
    }

    @LogExecutionTime
    public Flux<Profile> findByLastname(String lastname) {
        return crudRepository.findByLastnameOrderByFirstname(lastname);
    }

    @LogExecutionTime
    public Flux<Profile> findByFirstnameOrLastname(String firstname, String lastname) {
        return crudRepository.findByFirstnameOrLastnameOrderByLastname(firstname, lastname);
    }

    @LogExecutionTime
    public Mono<Profile> findByFirstnameAndLastname(String firstname, String lastname) {
        return crudRepository.findByFirstnameAndLastname(firstname, lastname);
    }

    @LogExecutionTime
    public Flux<Profile> findAll() {
        return crudRepository.findAll();
    }
}
