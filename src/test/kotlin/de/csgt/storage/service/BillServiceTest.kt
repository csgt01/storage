package de.csgt.storage.service

import de.csgt.storage.StorageApplication
import de.csgt.storage.model.Bill
import de.csgt.storage.model.Buy
import de.csgt.storage.model.Part
import de.csgt.storage.repository.BillRepository
import de.csgt.storage.repository.StorageRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import javax.transaction.Transactional

@SpringBootTest(classes = [(StorageApplication::class)])
@RunWith(SpringJUnit4ClassRunner::class)
class BillServiceTest {
    @Autowired
    lateinit var billService: BillService

    @Autowired
    lateinit var billRepository: BillRepository

    @Autowired
    lateinit var storageRepository: StorageRepository


    @Test
    @Transactional
    fun updateBill() {
        Assert.assertEquals(6, storageRepository.count())
        val bill = Bill(id = 19L)
        val buy = Buy(id = 39L, part = Part(id = 4L), bill = bill, quantity = 8, price = 10.0)
        bill.buys.add(buy)
//        copy.buys[0].quantity = 8
        billService.updateBill(19L, bill)

//        findById = billRepository.findById(19L).get()
        Assert.assertEquals(8, storageRepository.count())
    }

    @Test
    @Transactional
    fun updateBill2() {
        Assert.assertEquals(6, storageRepository.count())
        val bill = Bill(id = 19L)
        val buy = Buy(id = 39L, part = Part(id = 4L), bill = bill, quantity = 4, price = 10.0)
        bill.buys.add(buy)
        billService.updateBill(19L, bill)
        Assert.assertEquals(4, storageRepository.count())
    }

}
