package nz.co.orthodocx.repository.mongodb.reactive;

import nz.co.orthodocx.model.Profile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ProfileCrudRepositoryIntegrationTest {

    @Autowired
    private ProfileCrudRepository repository;

    private final Profile p1 = new Profile("0101", "Jack", "Sparrow");
    private final Profile p2 = new Profile("0102", "Jack", "Cole");
    private final Profile p3 = new Profile("0103", "Dave", "Jones");
    private final Profile p4 = new Profile("0104", "Dick", "Cole");

    @Before
    public void enableFluxDebug() {
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