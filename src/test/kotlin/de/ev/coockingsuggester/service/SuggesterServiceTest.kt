package de.ev.coockingsuggester.service

import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.model.DayInWeek
import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.RecipeRepository
import org.hamcrest.core.Is
import org.joda.time.LocalDate
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.internal.matchers.GreaterThan
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.data.domain.Pageable

@RunWith(MockitoJUnitRunner::class)
internal class SuggesterServiceTest {

    @Mock
    lateinit var cookingSuggestionRepository: CookingSuggestionRepository

    @Mock
    lateinit var recipeRepository: RecipeRepository

    @InjectMocks
    lateinit var suggesterService: SuggesterService

    val mockFoodTypes = hashMapOf<String, FoodType>(
            "Nudeln" to FoodType(name = "Nudeln"),
            "Gemüse" to FoodType(name = "Gemüse"),
            "Reis" to FoodType(name = "Reis"),
            "Kartoffeln" to FoodType(name = "Kartoffeln")
    )
    var mockRecipes = listOf(
            Recipe(
                    name = "Lasagne",
                    allowedOn = DayInWeek.Both,
                    foodTypes = setOf(
                            mockFoodTypes["Nudeln"] as FoodType
                    )
            ),
            Recipe(
                    name = "Spargel",
                    allowedOn = DayInWeek.Both,
                    foodTypes = setOf(
                            mockFoodTypes["Gemüse"] as FoodType
                    )
            )
    )

    var mockRecipesOrigin = mockRecipes.toList();
    var mockSuggestions = listOf(
            CookingSuggestion(
                    recipe = mockRecipes[0], date = LocalDate.now()
            )
    )
    var mockSuggestionsOrigin = mockSuggestions.toList();

    @Before
    fun setUp() {


        Mockito.doReturn(mockRecipes.filter {
            recipe -> (recipe
                .foodTypes
                ?.contains(
                        mockFoodTypes["Nudeln"]
                ))?.not() ?: false
        }).`when`(recipeRepository).findAllByFoodTypesNotIn(
                ArgumentMatchers.anyCollection()
        )


        Mockito.doReturn(mockSuggestions).`when`(cookingSuggestionRepository).findAllByDateBetween(
                LocalDate.now().minusDays(14), LocalDate.now().plusDays(1), Pageable.unpaged()
        )

    }

    @After
    fun tearDown() {
        mockRecipes = mockRecipesOrigin.toList()
        mockSuggestions = mockSuggestionsOrigin.toList()
    }

    @Test
    fun testPickSuggestionByPast() {
        var suggested = suggesterService.pickSuggestionByPast(
                mockSuggestions.subList(0,1),
                LocalDate.now().minusDays(1)
        )
        Assert.assertEquals(mockRecipes[1],suggested?.recipe)
    }

    @Test
    fun testgenerateMissingSuggestions() {
        var suggested = suggesterService.generateMissingSuggestions(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                mockSuggestions
        )
        Assert.assertTrue(suggested.size > 1)
        Assert.assertEquals(mockRecipes[1],suggested[1].recipe)
    }
}
