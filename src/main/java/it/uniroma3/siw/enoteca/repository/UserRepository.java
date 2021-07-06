package it.uniroma3.siw.enoteca.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.enoteca.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}