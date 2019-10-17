package de.ev.coockingsuggester.repository

import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(
        collectionResourceRel = "recipes",
        path = "/recipes",
        excerptProjection = RecipeProjection::class
)
interface RecipeRepository : PagingAndSortingRepository<Recipe, Long> {
    override fun findById(id: Long): Optional<Recipe>
    override fun <S : Recipe?> save(entity: S): S
    fun findAllByFoodTypesContains(foodType:FoodType) : List<Recipe>
    fun findAllByFoodTypesNotIn(foodType: Collection<FoodType>): List<Recipe>
}
