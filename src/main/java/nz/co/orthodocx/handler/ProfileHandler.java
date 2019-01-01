package nz.co.orthodocx.handler;

import lombok.extern.slf4j.Slf4j;
import nz.co.orthodocx.annotation.LogExecutionTime;
import nz.co.orthodocx.model.Profile;
import nz.co.orthodocx.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static nz.co.orthodocx.constants.Paths.FIRSTNAME;
import static nz.co.orthodocx.constants.Paths.LASTNAME;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class ProfileHandler {

    @Autowired
    private ProfileService profileService;

    @LogExecutionTime
    public Mono<ServerResponse> findByFirstname(ServerRequest request) {
        Flux<Profile> profileFlux = profileService.findByFirstname(
                request.pathVariable(FIRSTNAME.value()));
        return ok().body(profileFlux, Profile.class);
    }

    @LogExecutionTime
    public Mono<ServerResponse> findByLastname(ServerRequest request) {
        Flux<Profile> profileFlux = profileService.findByLastname(
                request.pathVariable(LASTNAME.value()));
        return ok().body(profileFlux, Profile.class);
    }

    @LogExecutionTime
    public Mono<ServerResponse> findByFirstnameOrLastname(ServerRequest request) {
        Flux<Profile> profileFlux = profileService.findByFirstnameOrLastname(
                request.pathVariable(FIRSTNAME.value()),
                request.pathVariable(LASTNAME.value()));
        return ok().body(profileFlux, Profile.class);
    }

    @LogExecutionTime
    public Mono<ServerResponse> findByFirstnameAndLastname(ServerRequest request) {
        Mono<Profile> profile = profileService.findByFirstnameAndLastname(
                request.pathVariable(FIRSTNAME.value()),
                request.pathVariable(LASTNAME.value()));
        return ok().body(profile, Profile.class);
    }

    @LogExecutionTime
    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<Profile> all = profileService.findAll();
        return ok().body(all, Profile.class);
    }
}