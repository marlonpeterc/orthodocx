package nz.co.orthodocx.repository.mongodb.reactive;

import nz.co.orthodocx.OrthodocxApplication;
import nz.co.orthodocx.model.Profile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = OrthodocxApplication.class)
public class ReactiveProfileRepositoryIntegrationTest {

    @Autowired
    private ReactiveProfileRepository repository;

    @Test
    public void findAll() {
        repository.deleteAll().block();
        Profile p1 = new Profile("Jack", "Sparrow");
        repository.save(p1).block();
        Flux<Profile> profileFlux = repository.findAll();
        StepVerifier.create(profileFlux)
                .assertNext(profile -> {
                    assertEquals("Jack", profile.getFirstname());
                    assertEquals("Sparrow" , profile.getLastname());
                    assertNotNull(profile.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void findByLastnameOrderByFirstname() {
        repository.deleteAll().block();
        Profile p1 = new Profile("Jack", "Sparrow");
        Profile p2 = new Profile("Jeck", "Sparrow");
        repository.saveAll(List.of(p1, p2)).blockLast();
        Flux<Profile> profileFlux = repository.findByLastnameOrderByFirstname("Sparrow", null);
        StepVerifier.create(profileFlux)
                .assertNext(profile -> {
                    assertEquals("Jack", profile.getFirstname());
                    assertEquals("Sparrow" , profile.getLastname());
                    assertNotNull(profile.getId());
                })
                .assertNext(profile -> {
                    assertEquals("Jeck", profile.getFirstname());
                    assertEquals("Sparrow" , profile.getLastname());
                    assertNotNull(profile.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void findByFirstnameAndLastname() {
        repository.deleteAll().block();
        Profile p1 = new Profile("Jack", "Sparrow");
        Profile p2 = new Profile("Jeck", "Sparrow");
        repository.saveAll(List.of(p1, p2)).blockLast();
        Mono<Profile> profileFlux = repository.findByFirstnameAndLastname("Jack", "Sparrow");
        StepVerifier.create(profileFlux)
                .assertNext(profile -> {
                    assertEquals("Jack", profile.getFirstname());
                    assertEquals("Sparrow" , profile.getLastname());
                    assertNotNull(profile.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void findFirstByLastname() {
        repository.deleteAll().block();
        Profile p1 = new Profile("Jack", "Sparrow");
        Profile p2 = new Profile("Jeck", "Sparrow");
        repository.saveAll(List.of(p1, p2)).blockLast();
        Mono<Profile> profileFlux = repository.findFirstByLastname("Sparrow");
        StepVerifier.create(profileFlux)
                .assertNext(profile -> {
                    assertEquals("Jack", profile.getFirstname());
                    assertEquals("Sparrow" , profile.getLastname());
                    assertNotNull(profile.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void findByFirstname() {
        repository.deleteAll().block();
        Profile p1 = new Profile("Jack", "Sparrow");
        Profile p2 = new Profile("Jack", "Cole");
        repository.saveAll(List.of(p1, p2)).blockLast();
        Flux<Profile> profileFlux = repository.findByFirstname("Jack");
        StepVerifier.create(profileFlux)
                .assertNext(profile -> {
                    assertEquals("Jack", profile.getFirstname());
                    assertEquals("Sparrow" , profile.getLastname());
                    assertNotNull(profile.getId());
                })
                .assertNext(profile -> {
                    assertEquals("Jack", profile.getFirstname());
                    assertEquals("Cole" , profile.getLastname());
                    assertNotNull(profile.getId());
                })
                .expectComplete()
                .verify();
    }
}