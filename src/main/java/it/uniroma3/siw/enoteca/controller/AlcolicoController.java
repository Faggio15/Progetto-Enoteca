package it.uniroma3.siw.enoteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.enoteca.controller.validator.AlcolicoValidator;
import it.uniroma3.siw.enoteca.model.Tipologia;
import it.uniroma3.siw.enoteca.model.CasaProduttrice;
import it.uniroma3.siw.enoteca.model.Alcolico;
import it.uniroma3.siw.enoteca.service.TipologiaService;
import it.uniroma3.siw.enoteca.service.CasaProduttriceService;
import it.uniroma3.siw.enoteca.service.AlcolicoService;

//vanno aggiunti metodi di: aggiunta di artista nell'opera corrente
//va veriricato il funzionamento di deleteOpera (specie il path e il return)
@Controller
public class AlcolicoController {
	
	@Autowired
	private AlcolicoService alcolicoService;
	
	@Autowired
	private CasaProduttriceService casaProduttriceService;
	
	@Autowired
	private TipologiaService tipologiaService;
	
    @Autowired
    private AlcolicoValidator alcolicoValidator;

    @RequestMapping(value="/admin/addAlcolico", method = RequestMethod.GET)
    public String addAlcolico(Model model) {
    	model.addAttribute("alcolico", new Alcolico());
    	model.addAttribute("tipologie", this.tipologiaService.tutti());
    	model.addAttribute("caseProduttrici", this.casaProduttriceService.tutti());
        return "admin/alcolicoForm";
    }
    

    @RequestMapping(value = "/alcolico/{id}", method = RequestMethod.GET)
    public String getAlcolico(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("alcolico", this.alcolicoService.alcolicoPerId(id));
    	model.addAttribute("tipologia", this.alcolicoService.alcolicoPerId(id).getTipologia());
    	model.addAttribute("casaProduttrice", this.alcolicoService.alcolicoPerId(id).getCasaProduttrice());
    	return "alcolico.html"; 
    }

    @RequestMapping(value = "/alcolici", method = RequestMethod.GET)
    public String getAlcolici(Model model) {
    		model.addAttribute("alcolici", this.alcolicoService.tutti());
    		return "alcolici.html";
    }
    
    
    @RequestMapping(value = "/admin/alcolico", method = RequestMethod.POST)
    public String newAlcolico(@ModelAttribute("alcolico") Alcolico alcolico,   @RequestParam("t") String tipologia,
    		@RequestParam("cp") String casaProduttrice, Model model, BindingResult bindingResult) {
    	this.alcolicoValidator.validate(alcolico, bindingResult);
        if (!bindingResult.hasErrors()) {
        	casaProduttrice.trim();
            List<CasaProduttrice> casProd= this.casaProduttriceService.casaProduttricePerNome(casaProduttrice);
        	alcolico.setCasaProduttrice(casProd.get(0));
        	tipologia.trim(); 
            List<Tipologia> tipol = this.tipologiaService.tipologiaPerNome(tipologia); 
        	alcolico.setTipologia(tipol.get(0));
        	this.alcolicoService.inserisci(alcolico);
            model.addAttribute("alcolici", this.alcolicoService.tutti());
            return "alcolici.html";
        }
        return "admin/alcolicoForm.html";
    }

    
    @RequestMapping(value= "/admin/deleteAlcolici", method= RequestMethod.GET)
    public String deleteAlcolicoGet(Model model) {
    	model.addAttribute("alcolici", this.alcolicoService.tutti());
    	return "admin/deleteAlcolico.html";
    }
    
    @RequestMapping(value = "/admin/deleteAlcolico/{id}", method = RequestMethod.POST)
	public String deleteAlcolicoPost(@PathVariable("id") Long id, Model model) {
		this.alcolicoService.deleteAlcolicoById(id);
		model.addAttribute("alcolici", this.alcolicoService.tutti());
		return "admin/deleteAlcolico.html";
	}
}