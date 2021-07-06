package it.uniroma3.siw.enoteca.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.enoteca.model.Alcolico;

import it.uniroma3.siw.enoteca.service.AlcolicoService;
import net.bytebuddy.asm.Advice.This;

@Controller
public class MainController {
	
	@Autowired
	private AlcolicoService alcolicoService;
	
	@RequestMapping(value = {"/", "home"}, method = RequestMethod.GET)
	public String home(Model model) {
	     List<Alcolico> alcolici = this.alcolicoService.tutti();
	     if(alcolici.size()>=3) {
	     Collections.shuffle(alcolici);
	     model.addAttribute("alcolici", alcolici.subList(0, 3));
	     }
		 return "home";
	}

}