package de.ev.coockingsuggester

import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.model.Recipe
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.RecipeRepository

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
internal class LoadDatabase {

    @Bean
    fun initDatabase(
            recipeRepository: RecipeRepository,
            cookingSuggestionRepository: CookingSuggestionRepository
    ): CommandLineRunner {
        return CommandLineRunner { args ->
            cookingSuggestionRepository.save(CookingSuggestion(date = Date(),recipe =
            recipeRepository.save(Recipe(name = "Lasagne", description = "", recipeText = ""))
            ))
        }
    }
}
