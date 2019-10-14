package de.ev.coockingsuggester.model

import javax.persistence.*

@Entity
class FoodType(
        @Id
        @Column
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = 0,

        @Column(nullable = false)
        var name: String,

        @ManyToMany(mappedBy = "foodTypes")
        var recipes: Set<Recipe>? = null
) {
}
