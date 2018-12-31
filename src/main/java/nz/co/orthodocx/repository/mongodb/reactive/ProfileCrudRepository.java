package nz.co.orthodocx.repository.mongodb.reactive;

import nz.co.orthodocx.model.Profile;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileCrudRepository extends ReactiveSortingRepository<Profile, String> {

    Flux<Profile> findByFirstname(String firstname);

    Flux<Profile> findByFirstnameOrLastnameOrderByLastname(String firstname, String lastname);

    Flux<Profile> findByLastnameOrderByFirstname(String lastname);

    Mono<Profile> findByFirstnameAndLastname(String firstname, String lastname);

    Mono<Profile> findFirstByLastname(String lastname);

}