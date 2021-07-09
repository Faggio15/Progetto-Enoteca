package it.uniroma3.siw.enoteca.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.enoteca.controller.validator.NazioneValidator;
import it.uniroma3.siw.enoteca.model.Alcolico;
import it.uniroma3.siw.enoteca.model.Nazione;
import it.uniroma3.siw.enoteca.service.NazioneService;
import it.uniroma3.siw.enoteca.util.FileUploadUtil;

@Controller
public class NazioneController {
	
	@Autowired
	private NazioneService nazioneService;
	
    @Autowired
    private NazioneValidator nazioneValidator;
    

    @RequestMapping(value="/admin/addNazione", method = RequestMethod.GET)
    public String addNazione(Model model) {
    	model.addAttribute("nazione", new Nazione());
        return "admin/nazioneForm.html";

    }

    @RequestMapping(value = "/nazione/{id}", method = RequestMethod.GET)
    public String getNazione(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("nazione", this.nazioneService.nazionePerId(id));
    	return "nazione.html";
    }

    @RequestMapping(value = "/nazioni", method = RequestMethod.GET)
    public String getNazioni(Model model) {
    		model.addAttribute("nazioni", this.nazioneService.tutti());
    		return "nazioni.html";
    }
    
    @RequestMapping(value = "/admin/nazione", method = RequestMethod.POST)
    public String newNazione(@ModelAttribute("nazione") Nazione nazione, @RequestParam("image") MultipartFile multipartFile, 
    									Model model, BindingResult bindingResult) throws IOException {
    	this.nazioneValidator.validate(nazione, bindingResult);
        if (!bindingResult.hasErrors()) {	
        	/*UPLOAD FOTO*/
        	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            nazione.setPhotos(fileName);
            Nazione savedNazione = this.nazioneService.inserisci(nazione);
            String uploadDir = "src/main/resources/static/img/nazioni/" + savedNazione.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        	
            model.addAttribute("nazioni", this.nazioneService.tutti());
            return "nazioni.html";
        }
        return "admin/nazioneForm.html";
    }
}