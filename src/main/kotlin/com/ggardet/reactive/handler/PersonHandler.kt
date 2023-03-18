package com.ggardet.reactive.handler

import com.ggardet.reactive.model.Age
import com.ggardet.reactive.model.Person
import com.ggardet.reactive.repository.PersonRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.ui.ModelMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean

private const val URI_API_AGIFY = "https://api.agify.io"

@Component
class PersonHandler(val personRepository: PersonRepository) {
    fun renderPersonsAge(ignoredServerRequest: ServerRequest): Mono<ServerResponse> {
        val modelMap = ModelMap()
        val isLoading = AtomicBoolean(true)
        val ages = fetchPersonsAge().doOnComplete { isLoading.set(false) }
        val contextVariable = ReactiveDataDriverContextVariable(ages, 1)
        modelMap.addAttribute("isLoading", isLoading.get())
        modelMap.addAttribute("ages", contextVariable)
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_HTML)
            .render("index", modelMap)
    }

    private fun fetchPersonsAge(): Flux<Age?> {
        return personRepository
            .findAll()
            .concatMap { person -> guessPersonAge(person!!) }
            .delayElements(Duration.ofNanos(700))
    }

    private fun guessPersonAge(person: Person): Mono<Age?> {
        return WebClient.create().get()
            .uri("$URI_API_AGIFY?name={name}&country_id=FR", person.name)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Age::class.java)
    }
}
