package de.ev.coockingsuggester.service

import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.model.DayInWeek
import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.RecipeRepository
import org.joda.time.LocalDate
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.data.domain.Pageable

@RunWith(MockitoJUnitRunner::class)
internal class SuggesterServiceTest {

    @Mock
    lateinit var cookingSuggestionRepository: CookingSuggestionRepository

    @Mock
    lateinit var recipeRepository: RecipeRepository

    @InjectMocks
    lateinit var suggesterServiceTest: SuggesterServiceTest

    @Before
    fun setUp() {
        val mockRecipes = listOf(
                Recipe(
                        name = "Lasagne",
                        allowedOn = DayInWeek.Both,
                        foodTypes = setOf(
                                FoodType(name = "Nudeln")
                        )
                ),
                Recipe(
                        name = "Spargel",
                        allowedOn = DayInWeek.Both,
                        foodTypes = setOf(
                                FoodType(name = "Gemüse")
                        )
                )
        )
        val mockSuggestions = listOf(
                CookingSuggestion(
                        recipe = mockRecipes[0], date = LocalDate.now()
                )
        )

        Mockito.doReturn(mockRecipes).`when`(recipeRepository).findAllByFoodTypesNotIn(
                setOf(
                        FoodType(name = "Nudeln")
                )
        )

        Mockito.doReturn(mockSuggestions).`when`(cookingSuggestionRepository).findAllByDateBetween(
                LocalDate.now().minusDays(7), LocalDate.now(), Pageable.unpaged()
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testPickSuggestionByPast() {

    }
}
