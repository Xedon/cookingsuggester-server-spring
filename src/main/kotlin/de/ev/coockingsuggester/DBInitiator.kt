package de.ev.coockingsuggester

import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.FoodTypeRepository
import de.ev.coockingsuggester.repository.RecipeRepository
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
internal class LoadDatabase {

    @Autowired
    @Bean
    fun initDatabase(
            recipeRepository: RecipeRepository,
            cookingSuggestionRepository: CookingSuggestionRepository,
            foodTypeRepository: FoodTypeRepository
    ): CommandLineRunner {
        return CommandLineRunner { args ->
            foodTypeRepository.saveAll(
                    listOf(
                            FoodType(name = "Nudeln"),
                            FoodType(name = "Reis"),
                            FoodType(name = "Kartoffeln")
                    )
            )

            cookingSuggestionRepository.save(
                    CookingSuggestion(date = Date(), recipe =
                    recipeRepository.save(
                            Recipe(
                                    name = "Lasagne",
                                    description = "Gemüse Lasagne",
                                    recipeText = "",
                                    foodTypes = setOf(
                                            foodTypeRepository.findAll().first()
                                    )
                            )
                    ))
            )
        }
    }
}
