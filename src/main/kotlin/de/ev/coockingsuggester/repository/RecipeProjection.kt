package de.ev.coockingsuggester.repository;

import de.ev.coockingsuggester.model.DayInWeek
import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineFoodType", types = [Recipe::class])
interface RecipeProjection {
    fun getDescription(): String
    fun getName(): String
    fun getSource(): String?
    fun getRecipeText(): String?
    fun getAllowedOn(): DayInWeek
    fun getFoodTypes(): Set<FoodType>?
}
