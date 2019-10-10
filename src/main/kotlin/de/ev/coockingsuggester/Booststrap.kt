package de.ev.coockingsuggester

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Booststrap {
    fun main(args: Array<String>) {
        SpringApplication.run(Booststrap::class.java, *args)
    }
}

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        Booststrap().main(args)
    }
}


