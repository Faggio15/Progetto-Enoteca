package it.uniroma3.siw.enoteca.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.enoteca.model.Tipologia;
import it.uniroma3.siw.enoteca.service.TipologiaService;

@Component
public class TipologiaValidator implements Validator {
	@Autowired
	private TipologiaService tipologiaService;
	
    private static final Logger logger = LoggerFactory.getLogger(TipologiaValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.tipologiaService.alreadyExists((Tipologia)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Tipologia.class.equals(aClass);
	}
}