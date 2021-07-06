package it.uniroma3.siw.enoteca.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.enoteca.model.Nazione;

@Repository
public interface NazioneRepository extends CrudRepository<Nazione, Long> {

	public List<Nazione> findByNome(String nome);
	}