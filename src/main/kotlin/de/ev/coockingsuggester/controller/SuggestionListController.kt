package de.ev.coockingsuggester.controller

import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class SuggestionListController {

    @Autowired
    lateinit var cookingSuggestionRepository: CookingSuggestionRepository

    @RequestMapping(
            method = [RequestMethod.GET],
            path = ["/api/v1/suggestions/search"],
            params = ["from", "to"]
    )
    fun suggestions(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("from") from: LocalDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("to") to: LocalDate,
            pageable: Pageable
    ): List<CookingSuggestion> {
        val tmpSuggestions = cookingSuggestionRepository.findAllByDateBetween(from, to,pageable);

        return emptyList()
    }


}
