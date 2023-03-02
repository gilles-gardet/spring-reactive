package com.ggardet.reactive.handler;

import com.ggardet.reactive.model.Age;
import com.ggardet.reactive.model.Person;
import com.ggardet.reactive.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PersonHandler {
    private final PersonRepository personRepository;

    public Mono<ServerResponse> names(ServerRequest serverRequest) {
        var modelMap = new ModelMap();
        Flux<Age> ages = this.fetchPersonAge();
        modelMap.addAttribute("title", "Age Guessing");
        modelMap.addAttribute("ages", ages);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .render("index", modelMap);
    }

    private Flux<Age> fetchPersonAge() {
        return this.personRepository
                .findAll()
                .flatMap(this::guessPersonAge);
    }

    private Mono<Age> guessPersonAge(Person person) {
        return WebClient.create().get()
                .uri("https://api.agify.io/?name={name}", person.getName())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Age.class);
    }
}
