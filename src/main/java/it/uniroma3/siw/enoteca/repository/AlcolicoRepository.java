package it.uniroma3.siw.enoteca.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.enoteca.model.Alcolico;

@Repository
public interface AlcolicoRepository extends CrudRepository<Alcolico, Long> {

	public List<Alcolico> findByNome(String titolo);
	
	public List<Alcolico> findByCasaProduttriceId(Long id);
	
	public List<Alcolico> findByTipologiaId(Long id);
}
