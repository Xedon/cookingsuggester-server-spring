package de.ev.coockingsuggester.model

import java.io.Serializable
import javax.persistence.*

@Entity
data class FoodType(
        @Id
        @Column
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = 0,

        @Column(nullable = false)
        var name: String
): Serializable {
}
