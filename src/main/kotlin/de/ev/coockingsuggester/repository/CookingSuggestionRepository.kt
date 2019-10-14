package de.ev.coockingsuggester.repository

import de.ev.coockingsuggester.model.CookingSuggestion
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*

@RepositoryRestResource(collectionResourceRel = "suggestions", path = "/suggestions")
interface CookingSuggestionRepository : PagingAndSortingRepository<CookingSuggestion, Long>{
    override fun findById(id: Long): Optional<CookingSuggestion>
}
