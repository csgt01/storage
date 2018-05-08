package de.csgt.storage.controller

import de.csgt.storage.model.Bill
import de.csgt.storage.model.Buy
import de.csgt.storage.repository.PartRepository
import de.csgt.storage.service.BillService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid


@Controller
@RequestMapping(value = ["bill"])
class BillController @Autowired constructor(val billService: BillService, val partRepository: PartRepository){

    @RequestMapping(value = [""], method = [(RequestMethod.GET)])
    fun list(model: Model): String {
        model.addAttribute("bills", billService.findAllBills())
        return "bill/bills"
    }


    @RequestMapping("/new")
    fun newBuy(model: Model): String {
        model.addAttribute("bill", Bill())
        model.addAttribute("partList", partRepository.findAll())
        return "bill/billform"
    }

    @RequestMapping("/{id}", method = [RequestMethod.GET])
    fun update(model: Model, @PathVariable id: Long): String {
        val findById = billService.findBillById(id)

        model.addAttribute("bill", findById)
        model.addAttribute("partList", partRepository.findAll())
        return "bill/billput"
    }


    @RequestMapping(value = ["/{id}"], params = ["update"], method = [(RequestMethod.POST)])
    fun updateSeedstarter(bill: Bill?, bindingResult: BindingResult, model: Model, id: Long): String {
        if (bindingResult.hasErrors()) {
            println("ERROR $bindingResult")
            model.addAttribute("bill", bill)
            model.addAttribute("partList", partRepository.findAll())

            return "bill/billput"
        }

        bill!!.createdAt = billService.findBillById(id).createdAt
        billService.updateBill(id, bill)
        return "redirect:/bill"
    }




    @RequestMapping(value = ["/{id}"], params = ["addRowUpdate"], method = [(RequestMethod.POST)])
    fun addRowUpdate(bill: Bill?, model: Model, @PathVariable id: Long): String {
        val b = billService.findBillById(id)
        b.buys.add(Buy(bill = b, price = 0.0, quantity = 0))
        model.addAttribute("bill", b)
        model.addAttribute("partList", partRepository.findAll())
        return "bill/billput"
    }

    @RequestMapping(value = [""], params = ["save"], method = [(RequestMethod.POST)])
    fun saveBill(@Valid bill: Bill, bindingResult: BindingResult, model: Model, redirectAttributes: RedirectAttributes): String {
        if (bill.buys.any { it.quantity < 1 }) {
            bindingResult.rejectValue("buys", "400", "Anzahl muss größer 0 sein!")
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("bill", bill)
            model.addAttribute("partList", partRepository.findAll())
            model.addAttribute("errorMessage", "Die Bill konnte nicht erzeugt werden, da das Formular Fehler enthält.")
            return "bill/billform"
        }
        billService.saveBill(bill)
        redirectAttributes.addFlashAttribute("successMessage", "Die Bill wurde angelegt.")
        return "redirect:/bill"
    }


    @RequestMapping(value = [""], params = ["addRow"], method = [(RequestMethod.POST)])
    fun addRow(bill: Bill?, bindingResult: BindingResult, model: Model): String {
        bill!!.buys.add(Buy(bill = bill!!, price = 0.0, quantity = 0))
        model.addAttribute("bill", bill)
        model.addAttribute("partList", partRepository.findAll())
        return "bill/billform"
    }


}