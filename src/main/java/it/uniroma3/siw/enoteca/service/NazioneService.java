package it.uniroma3.siw.enoteca.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.enoteca.model.Nazione;
import it.uniroma3.siw.enoteca.repository.NazioneRepository;

@Service
public class NazioneService {

	@Autowired
	private NazioneRepository nazioneRepository;
	
	@Transactional
	public Nazione inserisci(Nazione nazione) {
		return nazioneRepository.save(nazione);
	}
	
	@Transactional
	public List<Nazione> nazionePerNome(String nome) {
		return nazioneRepository.findByNome(nome);
	}
	

	@Transactional
	public List<Nazione> tutti() {
		return (List<Nazione>) nazioneRepository.findAll();
	}

	@Transactional
	public Nazione nazionePerId(Long id) {
		Optional<Nazione> optional = nazioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Nazione nazione) {
		List<Nazione> curatori = this.nazioneRepository.findByNome(nazione.getNome());
		if (curatori.size() > 0)
			return true;
		else 
			return false;
	}
}
