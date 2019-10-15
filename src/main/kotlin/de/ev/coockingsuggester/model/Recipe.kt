package de.ev.coockingsuggester.model

import java.io.Serializable
import javax.persistence.*

@Entity
class Recipe(
        @Id
        @Column
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = 0,

        @Column(nullable = false)
        var name: String = "",

        @Column
        var description: String? = null,

        @Column
        var source: String? = null,

        @Column(name = "recipe_text")
        var recipeText: String? = null,

        @Column(nullable = false)
        var allowedOn: DayInWeek = DayInWeek.Both,

        @OneToMany(mappedBy = "recipe")
        var suggestions: Set<CookingSuggestion> = setOf(),

        @Column
        @ManyToMany
        @JoinTable(name = "recipe_foodtypes")
        var foodTypes: Set<FoodType>? = null

) : Serializable {

}
