package de.ev.coockingsuggester.repository

import de.ev.coockingsuggester.model.CookingSuggestion
import org.joda.time.LocalDate
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@RepositoryRestResource(collectionResourceRel = "suggestions", path = "/suggestions")
interface CookingSuggestionRepository : PagingAndSortingRepository<CookingSuggestion, Long> {
    override fun findById(id: Long): Optional<CookingSuggestion>
    fun findAllByDateBetween(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("from") from: LocalDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("to") to: LocalDate,
            pageable: Pageable
    ): List<CookingSuggestion>
}
