package it.uniroma3.siw.enoteca.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.enoteca.model.Tipologia;
import it.uniroma3.siw.enoteca.repository.TipologiaRepository;

@Service
public class TipologiaService {

	@Autowired
	private TipologiaRepository tipologiaRepository;
	
	@Transactional
	public Tipologia inserisci(Tipologia tipologia) {
		return tipologiaRepository.save(tipologia);
	}
	
	@Transactional
	public List<Tipologia> tipologiaPerNome(String nome) {
		return tipologiaRepository.findByNome(nome);
	}

	@Transactional
	public List<Tipologia> tutti() {
		return (List<Tipologia>) tipologiaRepository.findAll();
	}

	@Transactional
	public Tipologia tipologiaPerId(Long id) {
		Optional<Tipologia> optional = tipologiaRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Tipologia tipologia) {
		List<Tipologia> artisti = this.tipologiaRepository.findByNome(tipologia.getNome());
		if (artisti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteTipologiaById(Long id) {
		this.tipologiaRepository.deleteById(id);
	}
}
