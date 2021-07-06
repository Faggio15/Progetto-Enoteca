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

import com.sun.xml.bind.v2.runtime.reflect.Accessor.SetterOnlyReflection;

import it.uniroma3.siw.enoteca.controller.validator.CasaProduttriceValidator;
import it.uniroma3.siw.enoteca.model.CasaProduttrice;
import it.uniroma3.siw.enoteca.model.Nazione;
import it.uniroma3.siw.enoteca.service.CasaProduttriceService;
import it.uniroma3.siw.enoteca.service.NazioneService;
import it.uniroma3.siw.enoteca.service.AlcolicoService;
import net.bytebuddy.asm.Advice.This;
//vanno aggiunti metodi di: aggiunta/rimozione opere e curatori nella collezione corrente
//va veriricato il funzionamento di deleteCollezione (specie il path e il return)
@Controller
public class CasaProduttriceController {
	
	@Autowired
	private CasaProduttriceService casaProduttriceService;
	
	@Autowired
	private NazioneService nazioneService;
	
	@Autowired
	private AlcolicoService alcolicoService;
	
	
    @Autowired
    private CasaProduttriceValidator casaProduttriceValidator;  

    @RequestMapping(value="/admin/addCasaProduttrice", method = RequestMethod.GET)
    public String addCasaProduttrice(Model model) {
    	model.addAttribute("casaProduttrice", new CasaProduttrice());
    	model.addAttribute("nazioni", this.nazioneService.tutti());
    	return "admin/casaProduttriceForm.html";
    	}

    @RequestMapping(value = "/casaProduttrice/{id}", method = RequestMethod.GET)
    public String getCasaProduttrice(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("casaProduttrice", this.casaProduttriceService.casaProduttricePerId(id));
    	model.addAttribute("nazione", this.casaProduttriceService.casaProduttricePerId(id).getNazione());
    	model.addAttribute("alcolici", this.alcolicoService.trovaPerCasaProduttriceId(id));
    	return "casaProduttrice.html"; 
    }
    
    @RequestMapping(value = "/CaseProduttrici", method = RequestMethod.GET)
    public String getCaseProduttrici(Model model) {
    		model.addAttribute("caseProduttrici", this.casaProduttriceService.tutti());
    		return "caseProduttrici.html";
    }
    
    @RequestMapping(value = "/admin/casaProduttrice", method = RequestMethod.POST)
    public String newCasaProduttrice(@ModelAttribute("casaProduttrice") CasaProduttrice casaProduttrice, @RequestParam("n") String nazione,
    									Model model, BindingResult bindingResult) {
    	this.casaProduttriceValidator.validate(casaProduttrice, bindingResult);
        if (!bindingResult.hasErrors()) {
            nazione.trim(); //elimino spazi bianchi iniziali e finali
            List<Nazione> naz = this.nazioneService.nazionePerNome(nazione); //prendo l'oggetto curatore
            casaProduttrice.setNazione(naz.get(0));  //setto il curatore della collezione 
            this.casaProduttriceService.inserisci(casaProduttrice);
            model.addAttribute("caseProduttrici", this.casaProduttriceService.tutti());
            return "caseProduttrici.html";
        }
        return "admin/casaProduttriceForm.html";
    }
    
    @RequestMapping(value= "/admin/deleteCaseProduttrici", method= RequestMethod.GET)
    public String deleteCasaProduttriceGet(Model model) {
    	model.addAttribute("caseProduttrici", this.casaProduttriceService.tutti());
    	return "admin/deleteCasaProduttrice.html";
    }
    
    @RequestMapping(value = "/admin/deleteCasaProduttrice/{id}", method = RequestMethod.POST)
	public String deleteCollezionePost(@PathVariable("id") Long id, Model model) {
		this.casaProduttriceService.deleteCasaProduttriceById(id);
		model.addAttribute("caseProduttrici", this.casaProduttriceService.tutti());
		return "admin/deleteCasaProduttrice.html";
	}
}