package com.example.accessingdatajpa

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

private val logger = LoggerFactory.getLogger(AccessingDataJpaApplication::class.java)

@SpringBootApplication
class AccessingDataJpaApplication {

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
        logger.info("Customers found with findAll():")
        logger.info("-------------------------------")
        repository.findAll().forEach { customer ->
            logger.info(customer.toString())
        }
        logger.info("")

        // fetch an individual customer by ID
        val customer = repository.findById(1L).orElseThrow()
        logger.info("Customer found with findById(1L):")
        logger.info("--------------------------------")
        logger.info(customer.toString())
        logger.info("")

        // fetch customers by last name
        logger.info("Customer found with findByLastName('Bauer'):")
        logger.info("--------------------------------------------")
        repository.findByLastName("Bauer").forEach { bauer ->
            logger.info(bauer.toString())
        }
        logger.info("")
    }
}

fun main(args: Array<String>) {
    runApplication<AccessingDataJpaApplication>(*args)
}