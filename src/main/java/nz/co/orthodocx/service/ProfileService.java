package nz.co.orthodocx.service;

import lombok.extern.slf4j.Slf4j;
import nz.co.orthodocx.annotation.LogExecutionTime;
import nz.co.orthodocx.event.ProfileCreatedEvent;
import nz.co.orthodocx.model.Profile;
import nz.co.orthodocx.repository.mongodb.reactive.ProfileCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@Service
public class ProfileService {

    @Autowired
    private ProfileCrudRepository crudRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

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

    @LogExecutionTime
    public Mono<Profile> update(String id, String firstname, String lastname) {
        return crudRepository
                .findById(id)
                .map(profile -> new Profile(profile.getId(), firstname, lastname))
                .flatMap(crudRepository::save)
                .doOnSuccess(publishEvent);
    }

    @LogExecutionTime
    public Mono<Profile> create(String firstname, String lastname) {
        return crudRepository
                .save(new Profile(null, firstname, lastname))
                .doOnSuccess(publishEvent);
    }

    @LogExecutionTime
    public Mono<Profile> delete(String id) {
        return crudRepository
                .findById(id)
                .flatMap(profile -> crudRepository
                        .deleteById(profile.getId())
                        .thenReturn(profile));
    }

    private Consumer<Profile> publishEvent =
            profile -> this.publisher.publishEvent(new ProfileCreatedEvent(profile));

}
