package it.uniroma3.siw.enoteca.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.enoteca.model.CasaProduttrice;

@Repository
public interface CasaProduttriceRepository extends CrudRepository<CasaProduttrice, Long> {

	public List<CasaProduttrice> findByNome(String nome);

}