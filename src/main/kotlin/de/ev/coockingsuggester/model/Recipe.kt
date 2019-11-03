package de.ev.coockingsuggester.model

import java.io.Serializable
import javax.persistence.*

@Entity
data class Recipe(
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

        @Column()
        var recipeText: String? = null,

        @Column(nullable = false)
        var allowedOn: DayInWeek = DayInWeek.Both,

        @Column
        @ManyToMany
        var foodTypes: Set<FoodType>? = null

) : Serializable {

}
