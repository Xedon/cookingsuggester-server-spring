package de.ev.coockingsuggester.service

import com.google.common.collect.Lists
import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.RecipeRepository
import org.apache.tomcat.util.http.fileupload.util.Streams
import org.joda.time.Days
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport
import kotlin.collections.LinkedHashSet

@Service
class SuggesterService {

    @Autowired
    lateinit var cookingSuggestionRepository: CookingSuggestionRepository

    @Autowired
    lateinit var recipeRepository: RecipeRepository

    fun pickSuggestionByPast(
            history: List<CookingSuggestion>,
            forDate: LocalDate
    ): CookingSuggestion {
        var suggestionTomorrow = history.find { forDate.plusDays(1).isEqual(it.date) }
        var suggestionYesterday = history.find { forDate.minusDays(1).isEqual(it.date) }
        var combinedFoodTypes = LinkedHashSet<Recipe>()
        combinedFoodTypes.addAll(((suggestionYesterday?.recipe?.foodTypes as Collection<Recipe>?)
                ?: emptySet<Recipe>()))
        combinedFoodTypes.addAll(((suggestionTomorrow?.recipe?.foodTypes as Collection<Recipe>?) ?: emptySet<Recipe>()))
        var recipeSuggestion = recipeRepository.findAllByFoodTypesNotIn(
                combinedFoodTypes as Collection<FoodType>
        )
        if (recipeSuggestion.isEmpty()) {
            recipeSuggestion = StreamSupport.stream(recipeRepository.findAll().spliterator(), false).collect(Collectors.toList())
        }

        if (recipeSuggestion.isEmpty())
            throw RuntimeException("No recipes available")

        return CookingSuggestion(
                date = forDate,
                recipe = recipeSuggestion[((Math.random() * 100000000).toInt()) % recipeSuggestion.size]
        )
    }

    fun generateMissingSuggestions(
            from: LocalDate,
            to: LocalDate,
            foundSuggestions: List<CookingSuggestion>
    ): List<CookingSuggestion> {
        val newSuggestions = foundSuggestions.toMutableList()
        val daysDifference = Days.daysBetween(from.minusDays(1), to).days;
        if (foundSuggestions.size < daysDifference) {
            var suggestionHistory = cookingSuggestionRepository.findAllByDateBetween(
                    from.minusDays(14),
                    to,
                    Pageable.unpaged()
            )
            var possibleDay: LocalDate = from;
            do {
                var suggestionFound = null != foundSuggestions.find { cookingSuggestion ->
                    cookingSuggestion.date == possibleDay
                }
                if(!suggestionFound){
                    var newSuggestion = pickSuggestionByPast(suggestionHistory, possibleDay)
                    newSuggestions.add(newSuggestion)
                }
                possibleDay = possibleDay.plusDays(1)
            } while (!possibleDay.isAfter(to))
        }
        return newSuggestions;
    }
}
