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
public class Bolnica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="BOLNICA_SEQ_GENERATOR", sequenceName="BOLNICA_SEQ",
	allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOLNICA_SEQ_GENERATOR")
	private int id;
	
	private String naziv;
	private String adresa;
	private double budzet;
	

	@OneToMany(mappedBy="bolnica", cascade= CascadeType.REMOVE)
	@JsonIgnore
	private List<Odeljenje> odeljenja;
	
	public Bolnica() {
		
	}
	
	public Bolnica(int id, String naziv, String adresa, double budzet) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.budzet = budzet;
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public double getBudzet() {
		return budzet;
	}

	public void setBudzet(double budzet) {
		this.budzet = budzet;
	}

	public List<Odeljenje> getOdeljenja() {
		return odeljenja;
	}

	public void setOdeljenja(List<Odeljenje> odeljenja) {
		this.odeljenja = odeljenja;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
}
