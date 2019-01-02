package nz.co.orthodocx.configuration;

import nz.co.orthodocx.constants.Routes;
import nz.co.orthodocx.handler.ProfileHandler;
import nz.co.orthodocx.model.Profile;
import nz.co.orthodocx.repository.mongodb.reactive.ProfileCrudRepository;
import nz.co.orthodocx.service.ProfileService;
import nz.co.orthodocx.test.data.ProfileTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.security.test.context.support.WithMockUser;

@WebFluxTest
//@WithMockUser
//@WithAnonymousUser
@Import({
        WebFluxConfig.class,
        ProfileHandler.class,
        ProfileService.class
})
public class WebFluxConfigTest implements ProfileTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProfileCrudRepository repository;

    @BeforeAll
    static void setUp() {
        Hooks.onOperatorDebug();
    }

    @Test
    public void findAll() {
        Mockito
                .when(repository.findAll())
                .thenReturn(Flux.just(p1, p2, p3, p4));

        webTestClient
                .get().uri(Routes.PROFILE_ALL.pattern())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(p1.getId())
                .jsonPath("$.[0].firstname").isEqualTo(p1.getFirstname())
                .jsonPath("$.[0].lastname").isEqualTo(p1.getLastname())
                .jsonPath("$.[1].id").isEqualTo(p2.getId())
                .jsonPath("$.[1].firstname").isEqualTo(p2.getFirstname())
                .jsonPath("$.[1].lastname").isEqualTo(p2.getLastname())
                .jsonPath("$.[2].id").isEqualTo(p3.getId())
                .jsonPath("$.[2].firstname").isEqualTo(p3.getFirstname())
                .jsonPath("$.[2].lastname").isEqualTo(p3.getLastname())
                .jsonPath("$.[3].id").isEqualTo(p4.getId())
                .jsonPath("$.[3].firstname").isEqualTo(p4.getFirstname())
                .jsonPath("$.[3].lastname").isEqualTo(p4.getLastname());
    }

    @Test
    public void findByFirstname() {
        Mockito
                .when(repository.findByFirstname("Jack"))
                .thenReturn(Flux.just(p1, p2));

        webTestClient
                .get().uri(Routes.PROFILE_BY_FIRSTNAME.pattern(), "Jack")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(p1.getId())
                .jsonPath("$.[0].firstname").isEqualTo(p1.getFirstname())
                .jsonPath("$.[0].lastname").isEqualTo(p1.getLastname())
                .jsonPath("$.[1].id").isEqualTo(p2.getId())
                .jsonPath("$.[1].firstname").isEqualTo(p2.getFirstname())
                .jsonPath("$.[1].lastname").isEqualTo(p2.getLastname());
    }

    @Test
    public void findByLastname() {
        Mockito
                .when(repository.findByLastnameOrderByFirstname("Cole"))
                .thenReturn(Flux.just(p2, p4));

        webTestClient
                .get().uri(Routes.PROFILE_BY_LASTNAME.pattern(), "Cole")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(p2.getId())
                .jsonPath("$.[0].firstname").isEqualTo(p2.getFirstname())
                .jsonPath("$.[0].lastname").isEqualTo(p2.getLastname())
                .jsonPath("$.[1].id").isEqualTo(p4.getId())
                .jsonPath("$.[1].firstname").isEqualTo(p4.getFirstname())
                .jsonPath("$.[1].lastname").isEqualTo(p4.getLastname());
    }

    @Test
    public void findByFirstnameOrLastname() {
        Mockito
                .when(repository.findByFirstnameOrLastnameOrderByLastname("Jack", "Cole"))
                .thenReturn(Flux.just(p2, p4, p1));

        webTestClient
                .get().uri(Routes.PROFILE_BY_FIRSTNAME_OR_LASTNAME.pattern(), "Jack", "Cole")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(p2.getId())
                .jsonPath("$.[0].firstname").isEqualTo(p2.getFirstname())
                .jsonPath("$.[0].lastname").isEqualTo(p2.getLastname())
                .jsonPath("$.[1].id").isEqualTo(p4.getId())
                .jsonPath("$.[1].firstname").isEqualTo(p4.getFirstname())
                .jsonPath("$.[1].lastname").isEqualTo(p4.getLastname())
                .jsonPath("$.[2].id").isEqualTo(p1.getId())
                .jsonPath("$.[2].firstname").isEqualTo(p1.getFirstname())
                .jsonPath("$.[2].lastname").isEqualTo(p1.getLastname());
    }

    @Test
    public void findByFirstnameAndLastname() {
        String expected = "{id: '0101', firstname: 'Jack', lastname: 'Sparrow'}";

        Mockito
                .when(repository.findByFirstnameAndLastname("Jack", "Sparrow"))
                .thenReturn(Mono.just(p1));

        webTestClient
                .get().uri(Routes.PROFILE_BY_FIRSTNAME_AND_LASTNAME.pattern(), "Jack", "Sparrow")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .json(expected);
    }

    @Test
    public void create() {
        String expected = "{id: '0101', firstname: 'Jack', lastname: 'Sparrow'}";

        Mockito
                .when(repository.save(Mockito.any(Profile.class)))
                .thenReturn(Mono.just(p1));

        webTestClient
                .post().uri(Routes.PROFILE_BY_FIRSTNAME_AND_LASTNAME.pattern(), "Jack", "Sparrow")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .json(expected);
    }

    @Test
    public void update() {
        String expected = "{id: '0101', firstname: 'Jack', lastname: 'Sparrow'}";

        Mockito
                .when(this.repository.findById(p1.getId()))
                .thenReturn(Mono.just(p1));
        Mockito
                .when(repository.save(Mockito.any(Profile.class)))
                .thenReturn(Mono.just(p1));

        webTestClient
                .put().uri(Routes.PROFILE_BY_ID_FNAME_LNAME.pattern(), "0101", "Jack", "Sparrow")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .json(expected);
    }

    @Test
    public void delete() {
        String expected = "{id: '0101', firstname: 'Jack', lastname: 'Sparrow'}";

        Mockito
                .when(this.repository.findById(p1.getId()))
                .thenReturn(Mono.just(p1));
        Mockito
                .when(this.repository.deleteById(p1.getId()))
                .thenReturn(Mono.empty());

        webTestClient
                .delete().uri(Routes.PROFILE_BY_ID.pattern(), "0101")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .json(expected);
    }
}