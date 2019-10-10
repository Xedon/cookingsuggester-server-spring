package de.ev.coockingsuggester.model

import java.io.Serializable
import javax.persistence.*

@Entity
class Recipe(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = 0,

        @Column
        var name: String? = null,

        @Column(nullable = false)
        var description: String? = null,

        @Column
        var source: String? = null,

        @Column(name = "recipe_text")
        var recipeText: String? = null
) : Serializable {

}
