package de.ev.coockingsuggester.repository

import de.ev.coockingsuggester.model.FoodType
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(collectionResourceRel = "foodType", path = "/foodtype")
interface FoodTypeRepository : PagingAndSortingRepository<FoodType, Long> {
    override fun findById(id: Long): Optional<FoodType>
}
