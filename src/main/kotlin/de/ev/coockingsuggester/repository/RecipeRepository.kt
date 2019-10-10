package de.ev.coockingsuggester.repository

import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.model.Recipe
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(collectionResourceRel = "/recipes", path = "/recipes")
interface RecipeRepository : PagingAndSortingRepository<Recipe, Long>{
    override fun findById(id: Long): Optional<Recipe>
}
