package de.ev.coockingsuggester

import de.ev.coockingsuggester.InitialGermanData.initialFoodTypes
import de.ev.coockingsuggester.InitialGermanData.initialRecipes
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.FoodTypeRepository
import de.ev.coockingsuggester.repository.RecipeRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
internal class LoadDatabase {

    var logger: Logger = LoggerFactory.getLogger(LoadDatabase::class.java)

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
                            initialFoodTypes
                    ).joinToString()
            )
            logger.info("Added " +
                    recipeRepository.saveAll(
                            initialRecipes(foodTypeRepository)
                    ).joinToString()
            )
        }
    }
}
