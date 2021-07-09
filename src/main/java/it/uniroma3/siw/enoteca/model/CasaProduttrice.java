package it.uniroma3.siw.enoteca.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

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

	@Column (length=2000)
	private String descrizione;
	
	@Column(nullable=false)
	private String sede;
	
	@Column(nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Long dataFondazione;
	
	private String fondatore;
	
	@OneToMany(mappedBy="casaProduttrice", cascade=CascadeType.ALL)
	List<Alcolico> alcoliciProdotti;
	
	@ManyToOne
	private Nazione nazione;
	
	public CasaProduttrice() {
		
	}
}
