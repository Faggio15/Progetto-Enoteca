package it.uniroma3.siw.enoteca.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class CasaProduttrice {

	
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@Column (nullable=false)
	private String nome;

	private String sede;
	
	private Long dataFondazione;
	
	@OneToMany(mappedBy="casaProduttrice")
	List<Alcolico> alcoliciProdotti;
	
	@ManyToOne
	private Nazione nazione;
	
	public CasaProduttrice() {
		
	}
}
