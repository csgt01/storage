package de.csgt.storage.repository

import de.csgt.storage.model.Bill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface BillRepository: JpaRepository<Bill, Long>