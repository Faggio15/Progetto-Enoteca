package it.uniroma3.siw.enoteca.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	
	
	//necessario per l'aggiunta dell'alcolico
	@Column(nullable=false)
	private String nome;
	
	@Column(length=2000)
	private String descrizione;
	
	@OneToMany(mappedBy="tipologia", cascade=CascadeType.ALL)
	List<Alcolico> alcolici;
	
	public Tipologia() {
		
	}
}
