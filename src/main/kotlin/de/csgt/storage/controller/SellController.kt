package de.csgt.storage.controller

import de.csgt.storage.model.Bill
import de.csgt.storage.model.Buy
import de.csgt.storage.model.Sell
import de.csgt.storage.model.SellPart
import de.csgt.storage.repository.SellRepository
import de.csgt.storage.repository.PartRepository
import de.csgt.storage.repository.StorageRepository
import de.csgt.storage.service.SellService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional


@Controller
@RequestMapping(value = "sell")
class SellController @Autowired constructor(val sellRepository: SellRepository, val partRepository: PartRepository, val sellService: SellService){

    @RequestMapping(value = "", method = arrayOf(RequestMethod.GET))
    fun list(model: Model): String {
        model.addAttribute("sells", sellService.findAllSells())
        return "sell/sells"
    }

//    @RequestMapping("/{id}")
//    fun showProduct(@PathVariable id: Long, model: Model): String {
//        model.addAttribute("sell", sellRepository.findById(id))
//        return "sell/sellshow.html"
//    }

    @RequestMapping("/new")
    fun newBuy(model: Model): String {
        model.addAttribute("sell", Sell())
        model.addAttribute("partList", partRepository.findAll())
        return "sell/sellform"
    }

    @RequestMapping("/{id}", method = [RequestMethod.GET])
    fun update(model: Model, @PathVariable id: Long): String {
        val findById = sellService.findSellById(id)

        model.addAttribute("sell", findById)
        model.addAttribute("partList", partRepository.findAll())
        return "sell/sellput"
    }

    @Transactional
    @RequestMapping(value = "/{id}", params = arrayOf("update"), method = arrayOf(RequestMethod.PUT))
    fun updateSeedstarter(sell: Sell?, bindingResult: BindingResult, model: Model, id: Long): String {
        if (bindingResult.hasErrors()) {
            println("ERROR $bindingResult")
            model.addAttribute("sell", sell)
            model.addAttribute("partList", partRepository.findAll())
            return "sell/sellput"
        }
        sell!!.createdAt =sellRepository.findById(id).get().createdAt
        sellService.updateSell(id, sell!!)
        return "redirect:/sell"
    }


    @RequestMapping(value = ["/{id}"], params = ["addRowUpdate"], method = [(RequestMethod.PUT)])
    fun addRowUpdate(sell: Sell, bindingResult: BindingResult, model: Model): String {
        sell.sellParts.add(SellPart(sell = sell, quantity = 0))
        model.addAttribute("sell", sell)
        model.addAttribute("partList", partRepository.findAll())
        return "sell/sellput"
    }

    @Transactional
    @RequestMapping(value = "", params = arrayOf("save"), method = arrayOf(RequestMethod.POST))
    fun saveSeedstarter(sell: Sell, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "sell/sellform"
        }
        sellService.saveSell(sell)
        return "redirect:/sell"
    }


    @RequestMapping(value = "", params = arrayOf("addRow"), method = arrayOf(RequestMethod.POST))
    fun addRow(sell: Sell, bindingResult: BindingResult, model: Model): String {
        sell.sellParts.add(SellPart(sell = sell, quantity = 0))
        model.addAttribute("sell", sell)
        model.addAttribute("partList", partRepository.findAll())
        return "sell/sellform"
    }


}