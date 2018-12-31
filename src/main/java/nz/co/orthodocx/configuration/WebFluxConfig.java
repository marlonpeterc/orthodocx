package nz.co.orthodocx.configuration;

import nz.co.orthodocx.handler.ProfileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static nz.co.orthodocx.constants.Routes.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebFluxConfig {

    private Logger log = LoggerFactory.getLogger(WebFluxConfig.class);

    @Bean
    public RouterFunction<ServerResponse> profileRoutes(ProfileHandler ph) {
        return route(GET(PROFILE_ALL.pattern()), ph::findAll)
                .andRoute(GET(PROFILE_BY_FIRSTNAME.pattern()), ph::findByFirstname)
                .andRoute(GET(PROFILE_BY_LASTNAME.pattern()), ph::findByLastname)
                .andRoute(GET(PROFILE_BY_FIRSTNAME_AND_LASTNAME.pattern()), ph::findByFirstnameAndLastname)
                .andRoute(GET(PROFILE_BY_FIRSTNAME_OR_LASTNAME.pattern()), ph::findByFirstnameOrLastname);
    }
}
