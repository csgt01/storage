package de.csgt.storage.service

import de.csgt.storage.model.Bill
import de.csgt.storage.model.Storage
import de.csgt.storage.repository.BillRepository
import de.csgt.storage.repository.PartRepository
import de.csgt.storage.repository.StorageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
class BillService @Autowired constructor(val billRepository: BillRepository, val partRepository: PartRepository, val storageRepository: StorageRepository){

    @Transactional
    fun updateBill(id: Long, bill: Bill) {
        val oldBill = billRepository.findById(id).orElseThrow { EntityNotFoundException("Not Found") }

        // vergleiche buys
        oldBill.buys.forEach { oldBuy ->
            bill.buys.find { it.id == oldBuy.id }.let { buy ->
                if (buy != null) {
                    buy.createdAt = oldBuy.createdAt
                    var del = buy.quantity - oldBuy.quantity
                    if (del < 0) { //weniger als vorher also loeschen
                        del *= (-1)
                        val findByPartAndPrice = storageRepository.findByPartAndPrice(oldBuy.part!!, oldBuy.price, PageRequest.of(0, del))
                        if (findByPartAndPrice.size < del) throw Exception("Zu wenig Storage")
                        val part = partRepository.findById(buy.part!!.id!!).get()
                        findByPartAndPrice.forEach {
                            findSt ->
                            part.storages.removeIf { it -> it.id == findSt.id }
                        }
                        partRepository.save(part)
                    } else if (del > 0) { //mehr als vorher, also anlegen
                        repeat(del, {
                            storageRepository.save(Storage(part = oldBuy.part!!, price = oldBuy.price))
                        })
                    }
                }
            }
        }
        bill.createdAt = oldBill.createdAt
        var save = billRepository.save(bill)

        save.buys.forEach { it.bill = save }
        save = billRepository.save(save)
        save.buys.filter { !oldBill.buys.contains(it) }.forEach { buy ->
            repeat(buy.quantity, {
                storageRepository.save(Storage(part = buy.part!!, price = buy.price))
            })
        }
    }

    @Transactional
    fun saveBill(bill: Bill) {
        var save = billRepository.save(bill)
        save.buys.forEach { it.bill = save }
        save = billRepository.save(save)
        save.buys.forEach { buy ->
            repeat(buy.quantity, {
                storageRepository.save(Storage(part = buy.part!!, price = buy.price))
            })
        }
    }

    fun findBillById(id: Long): Bill = billRepository.findById(id).orElseThrow { Exception("") }

    fun findAllBills(): MutableList<Bill> = billRepository.findAll()
}