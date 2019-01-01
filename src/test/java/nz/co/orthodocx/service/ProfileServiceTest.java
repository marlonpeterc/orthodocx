package nz.co.orthodocx.service;

import nz.co.orthodocx.model.Profile;
import nz.co.orthodocx.repository.mongodb.reactive.ProfileCrudRepository;
import nz.co.orthodocx.test.data.ProfileTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@DataMongoTest
@Import({ProfileService.class})
public class ProfileServiceTest implements ProfileTest {

    @Autowired
    private ProfileService service;

    @Autowired
    private ProfileCrudRepository repository;

    @BeforeAll
    static void enableFluxDebug() {
        Hooks.onOperatorDebug();
    }

    @Test
    public void findByFirstname() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findByFirstname("Jack");

        Publisher<Profile> fromRepository = Flux
                .from(setup)
                .thenMany(find);

        Publisher<Profile> actual = this.service
                .findByLastname("Jack")
                .thenMany(fromRepository);

        StepVerifier.create(actual)
                .expectNext(p1, p2)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByLastname() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findFirstByLastname("Sparrow");

        Publisher<Profile> fromRepository = Flux
                .from(setup)
                .thenMany(find);

        Publisher<Profile> actual = this.service
                .findByLastname("Sparrow")
                .thenMany(fromRepository);

        StepVerifier.create(actual)
                .expectNext(p1)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByFirstnameOrLastname() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findByFirstnameOrLastnameOrderByLastname("Jack", "Cole");

        Publisher<Profile> fromRepository = Flux
                .from(setup)
                .thenMany(find);

        Publisher<Profile> actual = this.service
                .findByFirstnameOrLastname("Jack", "Cole")
                .thenMany(fromRepository);

        StepVerifier.create(actual)
                .expectNext(p2, p4, p1)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByFirstnameAndLastname() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findByFirstnameAndLastname("Jack", "Sparrow");

        Publisher<Profile> fromRepository = Flux
                .from(setup)
                .thenMany(find);

        Publisher<Profile> actual = this.service
                .findByFirstnameAndLastname("Jack", "Sparrow")
                .thenMany(fromRepository);

        StepVerifier
                .create(actual)
                .expectNext(p1)
                .verifyComplete();
    }

    @Test
    public void findAll() {
        Flux<Profile> saved = repository.saveAll(Flux.just(p1, p2, p3, p4));

        Flux<Profile> composite = service.findAll().thenMany(saved);

        Predicate<Profile> match = profile -> saved.any(saveItem -> saveItem.equals(profile)).block();

        StepVerifier
                .create(composite)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete();
    }
}