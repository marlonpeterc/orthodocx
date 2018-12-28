package nz.co.orthodocx.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello")
public class ProfileController {

    @GetMapping("/{name}")
    private Mono<String> hello(@PathVariable String name) {
        return Mono.just("Hello " + name);
    }
}
