package de.csgt.storage.repository

import de.csgt.storage.model.Part
import de.csgt.storage.model.Sell
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface PartRepository: JpaRepository<Part, Long>