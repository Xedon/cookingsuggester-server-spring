package de.ev.coockingsuggester

import de.ev.coockingsuggester.model.FoodType
import de.ev.coockingsuggester.model.Recipe
import de.ev.coockingsuggester.repository.FoodTypeRepository

object InitialGermanData {

    val initialFoodTypes: List<FoodType> = listOf(
            FoodType(name = "Nudeln"),
            FoodType(name = "Reis"),
            FoodType(name = "Kartoffeln"),
            FoodType(name = "Gemüse")
    )

    fun initialRecipes(foodTypeRepository: FoodTypeRepository): List<Recipe> = listOf(
            Recipe(
                    name = "Lasagne",
                    description = "Gemüse Lasagne",
                    recipeText = "",
                    foodTypes = setOf(
                            foodTypeRepository.findByName("Nudeln").get()
                    )
            ),
            Recipe(
                    name = "Spaghetti mit Tomatensoße",
                    description = "Spaghetti mit Tomatensoße",
                    recipeText = "",
                    foodTypes = setOf(
                            foodTypeRepository.findByName("Nudeln").get()
                    )
            ),
            Recipe(
                    name = "Spaghetti mit Pesto",
                    description = "Spaghetti mit Pesto",
                    recipeText = "",
                    foodTypes = setOf(
                            foodTypeRepository.findByName("Nudeln").get()
                    )
            ),
            Recipe(
                    name = "Kartoffelecken mit Zaziki",
                    description = "Kartoffelecken mit Zaziki",
                    recipeText = "",
                    foodTypes = setOf(
                            foodTypeRepository.findByName("Kartoffeln").get()
                    )
            ),
            Recipe(
                    name = "Kartoffelgratin",
                    description = "Kartoffelgratin",
                    recipeText = "",
                    foodTypes = setOf(
                            foodTypeRepository.findByName("Kartoffeln").get()
                    )
            ),
            Recipe(
                    name = "Mienudeln",
                    description = "Mienudeln",
                    recipeText = "",
                    foodTypes = setOf(
                            foodTypeRepository.findByName("Nudeln").get()
                    )
            )
            ,
            Recipe(
                    name = "Überbackener Blumenkohl",
                    description = "Blumenkohl überbackeb mit Käse",
                    recipeText = "",
                    foodTypes = setOf(
                            foodTypeRepository.findByName("Gemüse").get()
                    )
            )
    )
}
