package de.ev.coockingsuggester.controller

import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.service.SuggesterService
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController()
class SuggestionListController {

    @Autowired
    lateinit var cookingSuggestionRepository: CookingSuggestionRepository

    @Autowired
    lateinit var suggesterService: SuggesterService


    @RequestMapping(
            method = [RequestMethod.GET],
            path = ["/api/v1/suggestions/search"],
            params = ["from", "to"],
            produces = ["application/json"]
    )
    fun suggestions(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam("from") from: LocalDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam("to") to: LocalDate,
            pageable: Pageable
    ): Page<CookingSuggestion> {
        val tmpSuggestions = cookingSuggestionRepository.findAllByDateBetween(from, to, pageable);
        var suggestions = suggesterService.generateMissingSuggestions(
                from,
                to,
                tmpSuggestions
        )

        if (pageable.sort.isSorted) {
            for (order in pageable.sort) {
                when (order.property) {
                    "date" -> {
                        suggestions = if (order.isAscending) {
                            suggestions.sortedBy { it.date }
                        } else {
                            suggestions.sortedByDescending { it.date }
                        }
                    }
                }
            }
        }

        return PageImpl(suggestions, pageable, suggestions.size.toLong())

    }

}
