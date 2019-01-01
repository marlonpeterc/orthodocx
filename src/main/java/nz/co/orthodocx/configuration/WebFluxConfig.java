package nz.co.orthodocx.configuration;

import lombok.extern.slf4j.Slf4j;
import nz.co.orthodocx.handler.ProfileHandler;
import nz.co.orthodocx.model.Profile;
import nz.co.orthodocx.repository.mongodb.reactive.ProfileCrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static nz.co.orthodocx.constants.Routes.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
public class WebFluxConfig {

    @Bean
    public RouterFunction<ServerResponse> profileRoutes(ProfileHandler ph) {
        return route(GET(PROFILE_ALL.pattern()), ph::findAll)
                .andRoute(GET(PROFILE_BY_FIRSTNAME.pattern()), ph::findByFirstname)
                .andRoute(GET(PROFILE_BY_LASTNAME.pattern()), ph::findByLastname)
                .andRoute(GET(PROFILE_BY_FIRSTNAME_AND_LASTNAME.pattern()), ph::findByFirstnameAndLastname)
                .andRoute(GET(PROFILE_BY_FIRSTNAME_OR_LASTNAME.pattern()), ph::findByFirstnameOrLastname);
    }
}
