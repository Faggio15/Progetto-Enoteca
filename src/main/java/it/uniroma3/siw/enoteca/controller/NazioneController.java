package it.uniroma3.siw.enoteca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.enoteca.controller.validator.NazioneValidator;
import it.uniroma3.siw.enoteca.model.Nazione;
import it.uniroma3.siw.enoteca.service.NazioneService;

@Controller
public class NazioneController {
	
	@Autowired
	private NazioneService nazioneService;
	
    @Autowired
    private NazioneValidator nazioneValidator;
    

    @RequestMapping(value="/addNazione", method = RequestMethod.GET)
    public String addNazione(Model model) {
    	model.addAttribute("nazione", new Nazione());
        return "nazioneForm.html"; //ancora non esiste
    }

    @RequestMapping(value = "/nazione/{id}", method = RequestMethod.GET)
    public String getNazione(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("nazione", this.nazioneService.nazionePerId(id));
    	return "nazione.html"; //ancora non esiste
    }

    @RequestMapping(value = "/nazioni", method = RequestMethod.GET)
    public String getNazioni(Model model) {
    		model.addAttribute("nazioni", this.nazioneService.tutti());
    		return "nazioni.html";//ancora non esiste
    }
    
    @RequestMapping(value = "/Nazione", method = RequestMethod.POST)
    public String newNazione(@ModelAttribute("nazione") Nazione nazione, 
    									Model model, BindingResult bindingResult) {
    	this.nazioneValidator.validate(nazione, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.nazioneService.inserisci(nazione);
            model.addAttribute("nazioni", this.nazioneService.tutti());
            return "nazioni.html";//ancora non esiste
        }
        return "nazioneForm.html";//ancora non esiste
    }
}