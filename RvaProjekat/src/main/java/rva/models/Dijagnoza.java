package rva.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
@Entity
public class Dijagnoza implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="DIJAGNOZA_SEQ_GENERATOR", sequenceName="DIJAGNOZA_SEQ",
	allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIJAGNOZA_SEQ_GENERATOR")
	private int id;
	
	private String naziv;
	private String opis;
	private String oznaka;
	
	
	@OneToMany(mappedBy="dijagnoza",cascade= CascadeType.REMOVE)
	@JsonIgnore
	private List<Pacijent> pacijenti;
	
	public Dijagnoza() {
		
	}

	public Dijagnoza(int id, String naziv, String opis, String oznaka) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.oznaka = oznaka;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public List<Pacijent> getPacijenti() {
		return pacijenti;
	}

	public void setPacijenti(List<Pacijent> pacijenti) {
		this.pacijenti = pacijenti;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
