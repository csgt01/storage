package de.csgt.storage

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

//import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
class StorageApplication

fun main(args: Array<String>) {
    runApplication<StorageApplication>(*args)
}
