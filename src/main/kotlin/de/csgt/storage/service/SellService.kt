package de.csgt.storage.service

import de.csgt.storage.model.Sell
import de.csgt.storage.model.Storage
import de.csgt.storage.repository.SellRepository
import de.csgt.storage.repository.PartRepository
import de.csgt.storage.repository.StorageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
class SellService @Autowired constructor(val sellRepository: SellRepository, val partRepository: PartRepository, val storageRepository: StorageRepository){

    @Transactional
    fun updateSell(id: Long, sell: Sell) {
//        val oldSell = sellRepository.findById(id).orElseThrow { EntityNotFoundException("Not Found") }
//
//        // vergleiche buys
//        oldSell.buys.forEach { oldBuy ->
//            sell.buys.find { it.id == oldBuy.id }.let { buy ->
//                if (buy != null) {
//                    buy.createdAt = oldBuy.createdAt
//                    var del = buy.quantity - oldBuy.quantity
//                    if (del < 0) { //weniger als vorher also loeschen
//                        del *= (-1)
//                        val findByPartAndPrice = storageRepository.findByPartAndPrice(oldBuy.part!!, oldBuy.price, PageRequest.of(0, del))
//                        if (findByPartAndPrice.size < del) throw Exception("Zu wenig Storage")
//                        val part = partRepository.findById(buy.part!!.id!!).get()
//                        findByPartAndPrice.forEach {
//                            findSt ->
//                            part.storages.removeIf { it -> it.id == findSt.id }
//                        }
//                        partRepository.save(part)
//                    } else if (del > 0) { //mehr als vorher, also anlegen
//                        repeat(del, {
//                            storageRepository.save(Storage(part = oldBuy.part!!, price = oldBuy.price))
//                        })
//                    }
//                }
//            }
//        }
//        sell.createdAt = oldSell.createdAt
//        var save = sellRepository.save(sell)
//
//        save.buys.forEach { it.sell = save }
//        save = sellRepository.save(save)
//        save.buys.filter { !oldSell.buys.contains(it) }.forEach { buy ->
//            repeat(buy.quantity, {
//                storageRepository.save(Storage(part = buy.part!!, price = buy.price))
//            })
//        }
    }

    @Transactional
    fun saveSell(sell: Sell) {
        var save = sellRepository.save(sell)
        save.sellParts.forEach { it.sell= save }
        save = sellRepository.save(save)
        save.sellParts.forEach { sellPart ->
            val findByPartByCreatedAtDesc = storageRepository.findByPartOrderByCreatedAtAsc(sellPart.part!!, PageRequest.of(0, sellPart.quantity))
            if (findByPartByCreatedAtDesc.size < sellPart.quantity) throw Exception("Zu wenig Storage")
            val part = partRepository.findById(sellPart.part!!.id!!).get()
            var price = 0.0
            findByPartByCreatedAtDesc.forEach { findSt ->
                price = price.plus(findSt.price)
                part.storages.removeIf {
                    it -> it.id == findSt.id
                }
            }
            sellPart.price = price
            partRepository.save(part)
        }
        save = sellRepository.save(save)
    }

    fun findSellById(id: Long): Sell = sellRepository.findById(id).orElseThrow { Exception("") }

    fun findAllSells(): MutableList<Sell> = sellRepository.findAll()
}