package it.uniroma3.siw.enoteca.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.enoteca.model.Tipologia;

@Repository
public interface TipologiaRepository extends CrudRepository<Tipologia, Long> {

	public List<Tipologia> findByNome(String nome);
}