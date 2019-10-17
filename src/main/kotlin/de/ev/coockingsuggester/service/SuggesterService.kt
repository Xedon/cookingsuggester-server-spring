package de.ev.coockingsuggester.service

import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.RecipeRepository
import org.joda.time.Days
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class SuggesterService {

    @Autowired
    lateinit var cookingSuggestionRepository: CookingSuggestionRepository

    @Autowired
    lateinit var recipeRepository: RecipeRepository

    fun pickSuggestionByPast(
            history: List<CookingSuggestion>,
            forDate: LocalDate
    ): CookingSuggestion? {
        var suggestionTomorrow = history.find { forDate.plusDays(1).isEqual(it.date) }
        var suggestionYesterday = history.find { forDate.minusDays(1).isEqual(it.date) }
        var recipeSuggestion = recipeRepository.findAllByFoodTypesNotIn(
                LinkedList<FoodType>().apply {
                    addAll(suggestionYesterday?.recipe?.foodTypes ?: emptyList())
                    addAll(suggestionTomorrow?.recipe?.foodTypes ?: emptyList())
                }
        )
        return CookingSuggestion(
                date = forDate,
                recipe = recipeSuggestion[((Math.random() * 100000000) as Int) % recipeSuggestion.size]
        )
    }

    fun generateMissingSuggestions(
            from: LocalDate,
            to: LocalDate,
            foundSuggestions: List<CookingSuggestion>
    ): List<CookingSuggestion> {
        val daysDifference = Days.daysBetween(from, to).days;
        if (foundSuggestions.size < daysDifference) {
            var suggestionHistory = cookingSuggestionRepository.findAllByDateBetween(
                    from.minusDays(14),
                    to,
                    Pageable.unpaged())
            val possibleDay: LocalDate = from;
            foundSuggestions.sortedBy(CookingSuggestion::date).forEach { suggesion ->
                if (!possibleDay.isEqual(suggesion.date)) {

                }
            }
        }
        return foundSuggestions;
    }
}
