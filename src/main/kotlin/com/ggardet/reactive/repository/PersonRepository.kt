package com.ggardet.reactive.repository

import com.ggardet.reactive.model.Person
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : ReactiveCrudRepository<Person, Long>
