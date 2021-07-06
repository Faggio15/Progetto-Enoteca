package it.uniroma3.siw.enoteca.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.enoteca.model.CasaProduttrice;
import it.uniroma3.siw.enoteca.service.CasaProduttriceService;

@Component
public class CasaProduttriceValidator implements Validator {
	@Autowired
	private CasaProduttriceService casaProduttriceService;
	
    private static final Logger logger = LoggerFactory.getLogger(CasaProduttriceValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sede", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataFondazione", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.casaProduttriceService.alreadyExists((CasaProduttrice)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return CasaProduttrice.class.equals(aClass);
	}
}