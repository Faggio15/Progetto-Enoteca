package it.uniroma3.siw.enoteca.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Tipologia {

	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	private String descrizione;
	
	@OneToMany(mappedBy="tipologia")
	List<Alcolico> alcolici;
	
	public Tipologia() {
		
	}
}
