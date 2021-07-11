package it.uniroma3.siw.enoteca.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.enoteca.model.Alcolico;
import it.uniroma3.siw.enoteca.model.User;
import it.uniroma3.siw.enoteca.repository.AlcolicoRepository;

@Service
public class AlcolicoService{

	@Autowired
	private AlcolicoRepository alcolicoRepository;

	@Transactional
	public Alcolico inserisci(Alcolico alcolico) {
		return alcolicoRepository.save(alcolico);
	}

	@Transactional
	public List<Alcolico> alcolicoPerNome(String nome) {
		return alcolicoRepository.findByNome(nome);
	}

	@Transactional
	public List<Alcolico> tutti() {
		return (List<Alcolico>) alcolicoRepository.findAll();
	}

	@Transactional
	public Alcolico alcolicoPerId(Long id) {
		Optional<Alcolico> optional = alcolicoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Alcolico alcolico) {
		List<Alcolico> opere = this.alcolicoRepository.findByNome(alcolico.getNome());
		if (opere.size() > 0)
			return true;
		else 
			return false;
	}
	
	public List<Alcolico> trovaPerCasaProduttriceId(Long id){
		return this.alcolicoRepository.findByCasaProduttriceId(id);
	}
	
	public List<Alcolico> trovaPerTipologiaId(Long id){
		return this.alcolicoRepository.findByTipologiaId(id);
	}

	public void deleteAlcolicoById(Long id) {
		alcolicoRepository.deleteById(id);
	}

	public List<Alcolico> cercaPerNomeLike(String cerca) {
		return this.alcolicoRepository.findByNomeContains(cerca);
	}

}