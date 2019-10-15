package de.ev.coockingsuggester.model

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
class CookingSuggestion(
        @Id
        @Column
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @OneToOne
        @JoinTable(name = "CookingSuggestion_Recipe")
        var recipe: Recipe,

        @Column
        var date: Date
) : Serializable

