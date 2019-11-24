package de.ev.coockingsuggester.service

import de.ev.coockingsuggester.InitialGermanData
import de.ev.coockingsuggester.model.CookingSuggestion
import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe
import de.ev.coockingsuggester.repository.CookingSuggestionRepository
import de.ev.coockingsuggester.repository.FoodTypeRepository
import de.ev.coockingsuggester.repository.RecipeRepository
import org.joda.time.LocalDate
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.annotation.Repeat
import java.util.*

@RunWith(MockitoJUnitRunner::class)
internal class SuggesterServiceTest {

    @Mock
    lateinit var cookingSuggestionRepository: CookingSuggestionRepository

    @Mock
    lateinit var recipeRepository: RecipeRepository

    @Mock
    lateinit var foodTypeRepository: FoodTypeRepository

    @InjectMocks
    lateinit var suggesterService: SuggesterService


    private lateinit var mockRecipes: List<Recipe>
    private lateinit var mockSuggestions: List<CookingSuggestion>

    @Before
    fun setUp() {

        Mockito.doAnswer { req ->
            Optional.ofNullable(InitialGermanData.initialFoodTypes.find {
                it.name == req.arguments[0] as String
            })
        }.`when`(foodTypeRepository).findByName((ArgumentMatchers.anyString()))

        mockRecipes = InitialGermanData.initialRecipes(foodTypeRepository)
        mockSuggestions = emptyList()

        Mockito.doAnswer { req ->
            val wantedFoodTypes = req.arguments[0] as Collection<FoodType>
            mockRecipes.filter { recipe ->
                recipe.foodTypes?.any { wantedFoodTypes.contains(it) }
                        ?: wantedFoodTypes.isEmpty() || wantedFoodTypes.isEmpty()
            }
        }.`when`(recipeRepository).findAllByFoodTypesNotIn(ArgumentMatchers.anyCollection())

        Mockito.doReturn(mockRecipes).`when`(recipeRepository).findAll()
/*
        Mockito.doReturn(mockSuggestions).`when`(cookingSuggestionRepository).findAllByDateBetween(
                LocalDate.now().minusDays(14), LocalDate.now().plusDays(1), Pageable.unpaged()
        )

 */

    }

    @Test
    fun noDuplicate() {
        var suggested = suggesterService.generateMissingSuggestions(
                LocalDate.now(),
                LocalDate.now().plusDays(6),
                mockSuggestions
        )
        Assert.assertEquals(7, suggested.size)
        Assert.assertEquals(7, suggested.map { it.recipe.name }.toSet().size)
    }

    @Test
    @Repeat(10)
    fun testPickSuggestionByPast() {
        var suggested = suggesterService.pickSuggestionByPast(
                mockSuggestions,
                LocalDate.now().minusDays(1)
        )
        Assert.assertNotNull(suggested)
    }
}
