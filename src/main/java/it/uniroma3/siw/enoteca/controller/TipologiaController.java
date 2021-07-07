package it.uniroma3.siw.enoteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.enoteca.controller.validator.TipologiaValidator;
import it.uniroma3.siw.enoteca.model.Tipologia;
import it.uniroma3.siw.enoteca.service.TipologiaService;
import it.uniroma3.siw.enoteca.service.AlcolicoService;

@Controller
public class TipologiaController {
	
	@Autowired
	private TipologiaService tipologiaService;
	
	@Autowired
	private AlcolicoService alcolicoService;
	
    @Autowired
    private TipologiaValidator tipologiaValidator;
    
    @RequestMapping(value="/admin/addTipologia", method = RequestMethod.GET)
    public String addTipologia(Model model) {
    	model.addAttribute("tipologia", new Tipologia());
        return "admin/tipologiaForm";
    }
    
    @RequestMapping(value = "/tipologia/{id}", method = RequestMethod.GET)
    public String getTipologiaPerId(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("tipologia", this.tipologiaService.tipologiaPerId(id));
    	model.addAttribute("alcolici", this.alcolicoService.trovaPerTipologiaId(id));
    	return "tipologia.html"; 
    }


    @RequestMapping(value = "/tipologie", method = RequestMethod.GET)
    public String getTipologie(Model model) {
    		model.addAttribute("tipologie", this.tipologiaService.tutti());
    		return "tipologie.html";
    }
    
    @RequestMapping(value = "/admin/tipologia", method = RequestMethod.POST)
    public String newTipologia(@ModelAttribute("tipologia") Tipologia tipologia, 
    									Model model, BindingResult bindingResult) {
    	this.tipologiaValidator.validate(tipologia, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.tipologiaService.inserisci(tipologia);
            model.addAttribute("tipologie", this.tipologiaService.tutti());
            return "tipologie.html";
        }
        return "admin/tipologiaForm";
    }
    
    @RequestMapping(value= "/admin/deleteTipologie", method= RequestMethod.GET)
    public String deleteArtistaGet(Model model) {
    	model.addAttribute("tipologie", this.tipologiaService.tutti());
    	return "admin/deleteTipologia.html";
    }
    
    @RequestMapping(value = "/admin/deleteTipologia/{id}", method = RequestMethod.POST)
	public String deleteArtistaPost(@PathVariable("id") Long id, Model model) {
		this.tipologiaService.deleteTipologiaById(id);
		model.addAttribute("tipologie", this.tipologiaService.tutti());
		return "admin/deleteTipologia.html";
	}
}