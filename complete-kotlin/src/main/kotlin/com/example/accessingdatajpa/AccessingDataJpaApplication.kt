package com.example.accessingdatajpa

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AccessingDataJpaApplication {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun demo(repository: CustomerRepository) = CommandLineRunner {
        // save a few customers
        repository.saveAll(
            listOf(
                Customer("Jack", "Bauer"),
                Customer("Chloe", "O'Brian"),
                Customer("Kim", "Bauer"),
                Customer("David", "Palmer"),
                Customer("Michelle", "Dessler")
            )
        )

        // fetch all customers
        log.info("Customers found with findAll():")
        log.info("-------------------------------")
        repository.findAll().forEach { customer ->
            log.info(customer.toString())
        }
        log.info("")

        // fetch an individual customer by ID
        val customer = repository.findById(1L).orElseThrow()
        log.info("Customer found with findById(1L):")
        log.info("--------------------------------")
        log.info(customer.toString())
        log.info("")

        // fetch customers by last name
        log.info("Customer found with findByLastName('Bauer'):")
        log.info("--------------------------------------------")
        repository.findByLastName("Bauer").forEach { bauer ->
            log.info(bauer.toString())
        }
        log.info("")
    }
}

fun main(args: Array<String>) {
    runApplication<AccessingDataJpaApplication>(*args)
}
