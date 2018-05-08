package de.csgt.storage.repository

import de.csgt.storage.model.Part
import de.csgt.storage.model.Storage
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface StorageRepository: JpaRepository<Storage, Long> {
    fun findByPartAndPrice(part: Part, price: Double, pageable: Pageable): List<Storage>
    fun findByPartOrderByCreatedAtAsc(part: Part, pageable: Pageable): List<Storage>
}