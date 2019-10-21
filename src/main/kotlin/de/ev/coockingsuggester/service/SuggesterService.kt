package de.ev.coockingsuggester.service

import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.RecipeRepository
import org.joda.time.Days
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ThreadLocalRandom
import java.util.stream.Collectors
import java.util.stream.StreamSupport

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
            recipeSuggestion = StreamSupport.stream(
                    recipeRepository.findAll().spliterator(),
                    false
            ).collect(Collectors.toList())
        }

        if (recipeSuggestion.isEmpty())
            throw RuntimeException("No recipes available")

        return CookingSuggestion(
                date = forDate,
                recipe =  recipeSuggestion[ThreadLocalRandom.current().nextInt(recipeSuggestion.size) ]
        )
    }

    @Transactional
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
            newSuggestions.addAll(suggestionHistory)
            do {
                var suggestionFound = null != foundSuggestions.find { cookingSuggestion ->
                    cookingSuggestion.date == possibleDay
                }
                if (!suggestionFound) {
                    var newSuggestion = pickSuggestionByPast(newSuggestions, possibleDay)
                    newSuggestions.add(newSuggestion)
                }
                possibleDay = possibleDay.plusDays(1)
            } while (!possibleDay.isAfter(to))
            cookingSuggestionRepository.saveAll(newSuggestions)
        }
        return newSuggestions;
    }
}
