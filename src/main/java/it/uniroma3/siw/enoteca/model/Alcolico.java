package it.uniroma3.siw.enoteca.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Alcolico {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@Column (nullable=false)
	private String nome;
	
	@Column (nullable=false)
	private Integer tassoAlcolico;
	
	@Column (nullable=false)
	private Long quantitaPresenti;
	
	@Column(nullable=false)
	private Long formato;
	
	//da discutere se è necessario
	private String luogoDiProduzione;
	
	@Column (length=2000)
	private String descrizione;
	
	private Long annata;
	
	@ManyToOne
	private Tipologia tipologia;
	
	@ManyToOne
	private CasaProduttrice casaProduttrice;
	
	public Alcolico() {
		
	}
	
}