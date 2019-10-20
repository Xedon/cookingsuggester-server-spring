package de.ev.coockingsuggester

import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.FoodTypeRepository
import de.ev.coockingsuggester.repository.RecipeRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
internal class LoadDatabase {

    var logger = LoggerFactory.getLogger(LoadDatabase::class.java)

    @Autowired
    @Bean
    fun initDatabase(
            recipeRepository: RecipeRepository,
            cookingSuggestionRepository: CookingSuggestionRepository,
            foodTypeRepository: FoodTypeRepository
    ): CommandLineRunner {
        return CommandLineRunner { args ->
            logger.info("Added " +
                    foodTypeRepository.saveAll(
                            listOf(
                                    FoodType(name = "Nudeln"),
                                    FoodType(name = "Reis"),
                                    FoodType(name = "Kartoffeln"),
                                    FoodType(name = "Gemüse")
                            )
                    ).joinToString()
            )
            logger.info("Added " +
                    recipeRepository.saveAll(
                            listOf(
                                    Recipe(
                                            name = "Lasagne",
                                            description = "Gemüse Lasagne",
                                            recipeText = "",
                                            foodTypes = setOf(
                                                    foodTypeRepository.findByName("Nudeln").orElseThrow()
                                            )
                                    ),
                                    Recipe(
                                            name = "Spaghetti mit Tomatensoße",
                                            description = "Spaghetti mit Tomatensoße",
                                            recipeText = "",
                                            foodTypes = setOf(
                                                    foodTypeRepository.findByName("Nudeln").orElseThrow()
                                            )
                                    ),
                                    Recipe(
                                            name = "Spaghetti mit Pesto",
                                            description = "Spaghetti mit Pesto",
                                            recipeText = "",
                                            foodTypes = setOf(
                                                    foodTypeRepository.findByName("Nudeln").orElseThrow()
                                            )
                                    ),
                                    Recipe(
                                            name = "Kartoffelecken mit Zaziki",
                                            description = "Kartoffelecken mit Zaziki",
                                            recipeText = "",
                                            foodTypes = setOf(
                                                    foodTypeRepository.findByName("Kartoffeln").orElseThrow()
                                            )
                                    )
                            )
                    ).joinToString()
            )
        }
    }
}
