package it.uniroma3.siw.enoteca.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import it.uniroma3.siw.enoteca.controller.validator.AlcolicoValidator;
import it.uniroma3.siw.enoteca.model.Tipologia;
import it.uniroma3.siw.enoteca.model.User;
import it.uniroma3.siw.enoteca.model.CasaProduttrice;
import it.uniroma3.siw.enoteca.model.Credentials;
import it.uniroma3.siw.enoteca.model.Alcolico;
import it.uniroma3.siw.enoteca.service.TipologiaService;
import it.uniroma3.siw.enoteca.service.UserService;
import it.uniroma3.siw.enoteca.util.FileUploadUtil;
import net.bytebuddy.asm.Advice.This;
import it.uniroma3.siw.enoteca.service.CasaProduttriceService;
import it.uniroma3.siw.enoteca.service.CredentialsService;
import it.uniroma3.siw.enoteca.service.AlcolicoService;

//vanno aggiunti metodi di: aggiunta di artista nell'opera corrente
//va veriricato il funzionamento di deleteOpera (specie il path e il return)
@Controller
public class AlcolicoController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CredentialsService credentialsService;
	
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
    		@RequestParam("cp") String casaProduttrice, @RequestParam("image") MultipartFile multipartFile, Model model, BindingResult bindingResult) throws IOException {
    	
        
    	this.alcolicoValidator.validate(alcolico, bindingResult);
        if (!bindingResult.hasErrors()) {
        	casaProduttrice.trim();
            List<CasaProduttrice> casProd= this.casaProduttriceService.casaProduttricePerNome(casaProduttrice);
        	alcolico.setCasaProduttrice(casProd.get(0));
        	tipologia.trim(); 
            List<Tipologia> tipol = this.tipologiaService.tipologiaPerNome(tipologia); 
        	alcolico.setTipologia(tipol.get(0));
        	
        	/*UPLOAD FOTO*/
        	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            alcolico.setPhotos(fileName);
            Alcolico savedAlcolico = this.alcolicoService.inserisci(alcolico);
            String uploadDir = "src/main/resources/static/img/alcolici/" + savedAlcolico.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            
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
    
    @RequestMapping(value = "/deletePreferiti/{id}", method = RequestMethod.POST)
   	public String deletePreferiti(@PathVariable("id") Long id, Model model) {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();
		List<Alcolico> alcoliciPref = user.getAlcoliciPreferiti();
		alcoliciPref.remove(this.alcolicoService.alcolicoPerId(id));
		List<User> utenti = this.alcolicoService.alcolicoPerId(id).getUtenti();
		utenti.remove(user);
		model.addAttribute("alcolici", alcoliciPref);
   		return "wishList.html";
   	}
    
    @RequestMapping(value = "/addPreferiti/{id}", method = RequestMethod.POST)
   	public String addPreferiti(@PathVariable("id") Long id, Model model) {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();
		List<Alcolico> alcoliciPref = user.getAlcoliciPreferiti();
		alcoliciPref.add(this.alcolicoService.alcolicoPerId(id));
		List<User> utenti = this.alcolicoService.alcolicoPerId(id).getUtenti();
		utenti.add(user);
		model.addAttribute("alcolici", this.alcolicoService.tutti());
   		return "alcolici.html";
   	}
    
    @RequestMapping(value = "/wishlist", method = RequestMethod.GET)
   	public String visualizzaPreferiti(Model model) {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();
		List<Alcolico> alcoliciPref = user.getAlcoliciPreferiti();
		model.addAttribute("alcolici", alcoliciPref);
   		return "wishlist.html";
   	}
}