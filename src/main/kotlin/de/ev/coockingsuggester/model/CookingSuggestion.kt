package de.ev.coockingsuggester.model

import org.joda.time.LocalDate
import java.io.Serializable
import javax.persistence.*

@Entity
data class CookingSuggestion(
        @Id
        @Column
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @ManyToOne
        var recipe: Recipe,

        @Column
        var date: LocalDate
) : Serializable

