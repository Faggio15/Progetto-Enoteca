package it.uniroma3.siw.enoteca.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.enoteca.model.CasaProduttrice;
import it.uniroma3.siw.enoteca.repository.CasaProduttriceRepository;

@Service
public class CasaProduttriceService {

	@Autowired
	private CasaProduttriceRepository casaProduttriceRepository;
	
	@Transactional
	public CasaProduttrice inserisci(CasaProduttrice casaProduttrice) {
		return casaProduttriceRepository.save(casaProduttrice);
	}
	
	@Transactional
	public List<CasaProduttrice> casaProduttricePerNome(String nome) {
		return casaProduttriceRepository.findByNome(nome);
	}

	@Transactional
	public List<CasaProduttrice> tutti() {
		return (List<CasaProduttrice>) casaProduttriceRepository.findAll();
	}

	@Transactional
	public CasaProduttrice casaProduttricePerId(Long id) {
		Optional<CasaProduttrice> optional = casaProduttriceRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(CasaProduttrice casaProduttrice) {
		List<CasaProduttrice> collezioni = this.casaProduttriceRepository.findByNome(casaProduttrice.getNome());
		if (collezioni.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public List<CasaProduttrice> trovaPerNazione(Long id){
		return casaProduttriceRepository.findByNazione(id);
	}

	@Transactional
	public void deleteCasaProduttriceById(Long id) {
		casaProduttriceRepository.deleteById(id);
	}


}
