package nz.co.orthodocx.repository.mongodb.reactive;

import nz.co.orthodocx.model.Profile;
import nz.co.orthodocx.test.data.ProfileTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

@DataMongoTest
public class ProfileCrudRepositoryIntegrationTest implements ProfileTest {

    @Autowired
    private ProfileCrudRepository repository;

    @BeforeAll
    static void enableFluxDebug() {
        Hooks.onOperatorDebug();
    }

    @Test
    public void findAll() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findAll();

        Publisher<Profile> composite = Flux
                .from(setup)
                .thenMany(find);

        StepVerifier.create(composite)
                .expectNext(p1, p2, p3, p4)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByLastnameOrderByFirstname() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findByLastnameOrderByFirstname("Sparrow");

        Publisher<Profile> composite = Flux
                .from(setup)
                .thenMany(find);

        StepVerifier.create(composite)
                .expectNext(p1)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByFirstnameAndLastname() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findByFirstnameAndLastname("Jack", "Sparrow");

        Publisher<Profile> composite = Flux
                .from(setup)
                .thenMany(find);

        StepVerifier.create(composite)
                .expectNext(p1)
                .expectComplete()
                .verify();
    }

    @Test
    public void findFirstByLastname() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findFirstByLastname("Sparrow");

        Publisher<Profile> composite = Flux
                .from(setup)
                .thenMany(find);

        StepVerifier.create(composite)
                .expectNext(p1)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByFirstname() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findByFirstname("Jack");

        Publisher<Profile> composite = Flux
                .from(setup)
                .thenMany(find);

        StepVerifier.create(composite)
                .expectNext(p1, p2)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByFirstnameOrLastnameOrderByLastname() {
        Publisher<Profile> setup = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(p1, p2, p3, p4)));

        Publisher<Profile> find = repository.findByFirstnameOrLastnameOrderByLastname("Jack", "Cole");

        Publisher<Profile> composite = Flux
                .from(setup)
                .thenMany(find);

        StepVerifier.create(composite)
                .expectNext(p2, p4, p1)
                .expectComplete()
                .verify();
    }
}