package de.ev.coockingsuggester.model

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
class CookingSuggestion(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @OneToOne(optional = false)
        var recipe: Recipe,

        @Column
        var date: Date
) : Serializable

