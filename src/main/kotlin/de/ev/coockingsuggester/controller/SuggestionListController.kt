package de.ev.coockingsuggester.controller

import de.ev.coockingsuggester.model.CookingSuggestion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import java.util.Collections

@RestController
class SuggestionListController @Autowired
constructor() {


    val suggestions: List<CookingSuggestion>
        @RequestMapping(method = [RequestMethod.PUT], path = ["/api/v1/createsuggestions"])
        get() = emptyList<CookingSuggestion>()
}
