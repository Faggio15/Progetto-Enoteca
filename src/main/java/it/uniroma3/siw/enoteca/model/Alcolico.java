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
	private Float gradoAlcolico;
	
	@Column (nullable=false)
	private Long quantitaPresenti;
	
	@Column (nullable=false)
	private Float prezzo;
	
	@Column (nullable=false)
	private String formato;
	
	@Column (length=2000)
	private String descrizione;
	
	//private String luogoDiProduzione;
	
	private Long annata;
	
	@Column(nullable = true, length = 64)
	private String photos;
	
	
	
	@ManyToOne
	private Tipologia tipologia;
	
	@ManyToOne
	private CasaProduttrice casaProduttrice;
	
	public Alcolico() {
		
	}
	
}