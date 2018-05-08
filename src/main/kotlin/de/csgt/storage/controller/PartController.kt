package de.csgt.storage.controller

import de.csgt.storage.model.Storage
import de.csgt.storage.model.Part
import de.csgt.storage.model.Buy
import de.csgt.storage.repository.PartRepository
import de.csgt.storage.repository.StorageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.time.LocalDateTime
import java.util.*
import org.springframework.web.bind.annotation.ModelAttribute




@Controller
@RequestMapping(value = "part")
class PartController @Autowired constructor(val partRepository: PartRepository, val storageRepository: StorageRepository){

    @RequestMapping(value = "", method = arrayOf(RequestMethod.GET))
    fun list(model: Model): String {
        model.addAttribute("parts", partRepository.findAll())
        return "part/parts"
    }

    @RequestMapping("/{id}", method = [RequestMethod.GET])
    fun update(model: Model, @PathVariable id: Long): String {
        val findById = partRepository.findById(id).get()

        model.addAttribute("part", findById)
        return "part/partput"
    }


    @RequestMapping(value = ["/{id}"], params = ["update"], method = [(RequestMethod.PUT)])
    fun updateSeedstarter(part: Part?, bindingResult: BindingResult, model: Model, id: Long): String {
        if (bindingResult.hasErrors()) {
            println("ERROR $bindingResult")
            model.addAttribute("part", part)
            return "part/partput"
        }
        part!!.createdAt = partRepository.findById(id).get().createdAt
        partRepository.save(part!!)
        return "redirect:/part"
    }

    @RequestMapping("/{id}/storage", method = [RequestMethod.GET])
    fun addStorage(@PathVariable id: Long, model: Model): String {
        val part = partRepository.findById(id)
        model.addAttribute("part", part.get())
        model.addAttribute("storage", Storage(price = 0.0, part = part.get()))
        return "part/storageform"
    }



    @RequestMapping("/{id}/storage", method = [RequestMethod.POST])
    fun addStorageSave(@PathVariable id: Long, storage: Storage, bindingResult: BindingResult, model: Model): String {
        val part = partRepository.findById(id)
        if (bindingResult.hasErrors()) {
            return "part/storageform"
        }
        storage.part = part.get()
        val s = storageRepository.save(storage);


        return "redirect:/part"
    }

    @RequestMapping("/new")
    fun newBuy(model: Model): String {
        model.addAttribute("part", Part())
        return "part/partform"
    }

    @RequestMapping(value = "", params = arrayOf("save"), method = arrayOf(RequestMethod.POST))
    fun saveSeedstarter(part: Part, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "part/partform"
        }
        partRepository.save(part)
        return "redirect:/part"
    }


    @RequestMapping(value = "", params = arrayOf("addRow"), method = arrayOf(RequestMethod.POST))
    fun addRow(part: Part, bindingResult: BindingResult, model: Model): String {
        model.addAttribute("part", part)
        return "part/partform"
    }


}