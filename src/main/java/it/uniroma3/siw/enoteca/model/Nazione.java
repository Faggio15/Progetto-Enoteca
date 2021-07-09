package it.uniroma3.siw.enoteca.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Nazione {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable = true, length = 64)
	private String photos;
	
	@OneToMany(mappedBy="nazione", cascade=CascadeType.ALL)
	List<CasaProduttrice> caseProduttrici;
	
	public Nazione() {
		
	}
	
	   @Transient
	    public String getPhotosImagePath() {
	        if (photos == null || id == null) return null;
	         
	        return "/img/nazioni/" + id + "/" + photos;
	    }
}
